package betterwithage

import org.codehaus.groovy.grails.commons.ConfigurationHolder

class RapleafService {

    def determineAgeAndGender(contacts) {
        def body = ''

        contacts.each { contact ->
            if (body)
                body += ','
            body +=
"""
{
\"email\":\"${contact.email}\",
\"first\":\"${contact.firstName}\",
\"last\":\"${contact.lastName}\",
\"street\":\"${contact.address}\",
\"city\":\"${contact.city}\",
\"state\":\"${contact.state}\",
\"zip\":\"${contact.zip}\"
}
"""
        }

        body = "[${body}]"

        withRest(uri: ConfigurationHolder.config.rapleaf.apiURL) {
            def response = post(
                path: ConfigurationHolder.config.rapleaf.apiPath,
                query: [api_key: ConfigurationHolder.config.rapleaf.apiKey],
                body: body,
                contentType : groovyx.net.http.ContentType.JSON)

            if (response.status != 200)
                throw new RuntimeException("${response.data} - error code ${response.status}")

            response.data.eachWithIndex { info, i ->
                def gender
                switch (info.gender) {
                case 'Male':
                    gender = Gender.MALE
                    break
                case 'Female':
                    gender = Gender.FEMALE
                    break
                default:
                    gender = Gender.UNKNOWN
                    break
                }
                contacts[i].gender = gender
                contacts[i].ageRange = info.age
            }
        }
    }
}
