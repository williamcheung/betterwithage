package betterwithage

class BetterWithAgeController {

    def shoeboxedService

    def index = {}

    def search = {
        shoeboxedService.importContacts(params.email, params.password)
        redirect(controller: 'businessContact')
    }
}
