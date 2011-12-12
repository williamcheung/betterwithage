<html>
    <head>
        <title>Better With Age</title>
    </head>
    <body>
        <center>
            <h1>Better With Age</h1>
            <p>Makes <a href="http://www.shoeboxed.com/"><b>Shoeboxed</b></a>
            even better by finding the <i>ages of your biz contacts</i>.</p>
            <p><font size="-1">This app is running on open source software:</font></p>
            <form>
                <g:set var="grailsImage" value="${resource(dir:'images', file:'grails_logo.jpg')}"></g:set>
                <a href="http://grails.org"><img src="${grailsImage}" border="0"/></a>
                <table>
                    <tr>
                        <td/>
                        <td align="center"><b>Grab contacts from Shoeboxed</b></td>
                    </tr>
                    <tr>
                        <td>Email Address or Username:</td>
                        <td align="left"><input name="email" size="34"></td>
                    </tr>
                    <tr>
                        <td>Shoeboxed Password:</td>
                        <td align="left"><input type="password" name="password" size="34"></td>
                    </tr>
                    <tr>
                        <td/>
                        <td align="center">
                            <g:actionSubmit value="grab contacts" action="search"/>
                        </td>
                    </tr>
                    <tr>
                        <td/>
                        <td align="center">
                            <g:link controller="businessContact">view contacts with age</g:link>
                        </td>
                    </tr>
                </table>
            </form>
        </center>
    </body>
</html>
