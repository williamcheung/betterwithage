package betterwithage

import groovy.xml.StreamingMarkupBuilder

import java.text.SimpleDateFormat

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class ShoeboxedService {

    private static final PAGE_SIZE = 200

    def getContacts(email, password) {
        def userToken = login(email, password)

        def contacts = []
        def pageNumber = 1
        def more = true

        while (more) {
            def request = buildBusinessCardRequest(userToken, pageNumber++)
            def responseXml = sendToShoeboxed(request)

            responseXml.BusinessCards.BusinessCard.each { card ->
                def contact = buildBusinessContact(card)
                contacts << contact
            }

            def total = "${responseXml.BusinessCards.@count}" as int
            if (contacts.size == total)
                more = false
        }

        contacts
    }

    private def login(email, password) {
        def request = buildLoginRequest(email, password)
        def responseXml = sendToShoeboxed(request)
        "${responseXml.SbxUserToken}"
    }

    private def buildLoginRequest(email, password) {
        def request = {
            Request(xmlns: 'urn:sbx:apis:SbxBaseComponents') {
                RequesterCredentials {
                    ApiUserName ConfigurationHolder.config.shoeboxed.apiUserName
                    ApiUserToken ConfigurationHolder.config.shoeboxed.apiUserToken
                }
                GetLoginCall {
                    UserName "${email}"
                    UserPassword "${password}"
                }
            }
        }

        new StreamingMarkupBuilder().bind(request)
    }

    private def buildBusinessCardRequest(shoeboxedUserToken, pageNumber) {
        def request = {
            Request(xmlns: 'urn:sbx:apis:SbxBaseComponents') {
                RequesterCredentials {
                    ApiUserToken ConfigurationHolder.config.shoeboxed.apiUserToken
                    SbxUserToken "${shoeboxedUserToken}"
                }
                GetBusinessCardCall {
                    BusinessCardFilter {
                        Results "${PAGE_SIZE}"
                        PageNo "${pageNumber}"
                    }
                }
            }
        }

        new StreamingMarkupBuilder().bind(request)
    }

    private def buildBusinessContact(card) {
        new BusinessContact(
            externalId: "${card.@id}",
            firstName: "${card.@firstName}",
            lastName: "${card.@lastName}",
            createDate: new SimpleDateFormat('MM/dd/yyyy').parse("${card.@createDate}"),
            address: "${card.@address}",
            address2: "${card.@address2}",
            city: "${card.@city}",
            state: "${card.@state}",
            zip: "${card.@zip}",
            country: "${card.@country}",
            email: "${card.@email}",
            website: "${card.@website}",
            company: "${card.@company}",
            title: "${card.@position}",
            workPhone: "${card.@workPhone}",
            cellPhone: "${card.@cellPhone}",
            businessCardImageURL: new URL("${card.@frontImgUrl}"),
            note: "${card.@note}")
    }

    private def sendToShoeboxed(request) {
        withRest(uri: ConfigurationHolder.config.shoeboxed.apiURL) {
            def response = post(
                path: ConfigurationHolder.config.shoeboxed.apiPath,
                body: "xml=${request}",
                requestContentType: groovyx.net.http.ContentType.URLENC)

            assert response.status == 200
            assert response.headers."Content-Type".startsWith('text/xml')
            def responseXml = response.data

            def error
            if (responseXml.name() == 'Error')
                error = responseXml
            else if (!responseXml.Error.isEmpty())
                error = responseXml.Error
            if (error)
                throw new RuntimeException("${error.@description} - error code ${error.@code}")

            return responseXml
        }
    }
}
