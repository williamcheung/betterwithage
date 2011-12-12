package betterwithage

class BetterWithAgeController {

    def shoeboxedService
    def rapleafService

    def index = {}

    def search = {
        def contacts = shoeboxedService.getContacts(params.email, params.password)
        rapleafService.determineAgeAndGender(contacts)

        contacts.each { contact ->
            def existingContact = BusinessContact.findByExternalId(contact.externalId)
            if (existingContact) {
                existingContact.properties = contact.properties
                contact = existingContact
            }
            contact = contact.save(flush: true, failOnError: true)
        }

        redirect(controller: 'businessContact')
    }
}
