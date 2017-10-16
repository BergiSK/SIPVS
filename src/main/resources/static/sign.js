function signTheForm(){


    var oXML = new ActiveXObject("DSig.XadesSigAtl");
    var oXMLPlugin = new ActiveXObject("DSig.XmlPluginAtl");
    var xsd = '<schema xmlns="http://www.w3.org/2001/XMLSchema"\n' +
        '        xmlns:sipvs="http://www.example.org/sipvs"\n' +
        '        targetNamespace="http://www.example.org/sipvs"\n' +
        '        elementFormDefault="qualified">\n' +
        '\n' +
        '    <element name="team" type="sipvs:Team"></element>\n' +
        '\n' +
        '    <complexType name="User">\n' +
        '        <sequence>\n' +
        '            <element name="firstName" type="string" minOccurs="0" maxOccurs="1" />\n' +
        '            <element name="lastName" type="string" />\n' +
        '            <element name="age" type="integer" />\n' +
        '            <element name="mail" type="string" />\n' +
        '        </sequence>\n' +
        '    </complexType>\n' +
        '\n' +
        '    <complexType name="Team">\n' +
        '        <group ref="sipvs:TeamGroup"/>\n' +
        '        <attribute name="shortcut" type="string" use="required" />\n' +
        '    </complexType>\n' +
        '\n' +
        '    <group name="TeamGroup">\n' +
        '        <sequence>\n' +
        '            <element name="user" type="sipvs:User" />\n' +
        '            <element name="name" type="string" />\n' +
        '            <element name="vip" type="boolean" />\n' +
        '            <sequence minOccurs="3" maxOccurs="3">\n' +
        '                <element name="player" type="string"/>\n' +
        '            </sequence>\n' +
        '        </sequence>\n' +
        '    </group>\n' +
        '\n' +
        '\n' +
        '\n' +
        '\n' +
        '</schema>';
    var xsdURI = "http://dis-major/dppo.xsd";
    var xsl = '<?xml version="1.0" encoding="utf-8"?>\n' +
        '<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sipvs="http://www.example.org/sipvs" xmlns:fo="http://www.w3.org/1999/XSL/Format" >\n' +
        '    <xsl:output method="html"/>\n' +
        '\n' +
        '    <xsl:template match="/">\n' +
        '        <html>\n' +
        '            <head>\n' +
        '                <link rel = "stylesheet"\n' +
        '                      href = "https://fonts.googleapis.com/icon?family=Material+Icons"></link>\n' +
        '                <link rel = "stylesheet"\n' +
        '                      href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css"></link>\n' +
        '                <script type = "text/javascript"\n' +
        '                        src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>\n' +
        '                <script src = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js">\n' +
        '                </script>\n' +
        '                <link rel="stylesheet" type="text/css" media="all" href="../styles.css" />\n' +
        '            </head>\n' +
        '            <body>\n' +
        '            <div class="row col s12 row body">\n' +
        '                <h3 class="center" >Details</h3>\n' +
        '                <h5>Team</h5>\n' +
        '\n' +
        '                <!-- TEAM NAME -->\n' +
        '                <xsl:apply-templates select="//sipvs:name"/>\n' +
        '\n' +
        '                <!-- TEAM NAME SHORTCUT -->\n' +
        '                <div class="col s6">\n' +
        '                    <div class="left label">Shortcut:</div>\n' +
        '                    <div class="left value">\n' +
        '                        <xsl:value-of select="//@shortcut" />\n' +
        '                    </div>\n' +
        '                </div>\n' +
        '\n' +
        '                <!-- ALL PLAYERS -->\n' +
        '                <xsl:for-each select="//sipvs:player">\n' +
        '                    <xsl:variable name="i" select="position()" />\n' +
        '                    <div class="col s6">\n' +
        '                        <div class="left label">\n' +
        '                            Player\n' +
        '                        <xsl:copy>\n' +
        '                            <xsl:value-of select="$i"/>\n' +
        '                        </xsl:copy>\n' +
        '                            :\n' +
        '                         </div>\n' +
        '                         <div class="left value">\n' +
        '                             <xsl:value-of select="."/>\n' +
        '                         </div>\n' +
        '                    </div>\n' +
        '                </xsl:for-each>\n' +
        '\n' +
        '                <!-- VIP room -->\n' +
        '                <xsl:apply-templates select="//sipvs:vip"/>\n' +
        '                <div class="row"></div>\n' +
        '                <hr/>\n' +
        '\n' +
        '                    <h5>Contact</h5>\n' +
        '\n' +
        '\n' +
        '                <!-- User -->\n' +
        '                <xsl:apply-templates select="//sipvs:user"/>\n' +
        '                <div class="row"></div>\n' +
        '                <div class="row back">\n' +
        '                    <button class="waves-effect waves-light btn" onclick="window.location.href=\'/form\'"><i class="material-icons left">arrow_back</i>Back</button>\n' +
        '                </div>\n' +
        '            </div>\n' +
        '            </body>\n' +
        '        </html>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:name">\n' +
        '        <div class="col s6">\n' +
        '            <div class="left label">Team Name:</div>\n' +
        '            <div class="left value">\n' +
        '                <xsl:value-of select="."/>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:vip">\n' +
        '        <div class="col s6">\n' +
        '            <div class="left label">VIP room:</div>\n' +
        '            <div class="left value">\n' +
        '                <xsl:choose>\n' +
        '                    <xsl:when test="contains(./text(),\'true\')">\n' +
        '                        Yes\n' +
        '                    </xsl:when>\n' +
        '                    <xsl:otherwise>\n' +
        '                        No\n' +
        '                    </xsl:otherwise>\n' +
        '                </xsl:choose>\n' +
        '            </div>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:user">\n' +
        '        <div class="col s6">\n' +
        '            <div class="left label">Name:</div>\n' +
        '            <xsl:apply-templates select="//sipvs:firstName"/>\n' +
        '        </div>\n' +
        '        <div class="col s6">\n' +
        '            <div class="left label">Surname:</div>\n' +
        '            <xsl:apply-templates select="//sipvs:lastName"/>\n' +
        '        </div>\n' +
        '        <div class="col s6">\n' +
        '            <div class="left label">Age:</div>\n' +
        '            <xsl:apply-templates select="//sipvs:age"/>\n' +
        '        </div>\n' +
        '        <div class="col s6">\n' +
        '            <div class="left label">e-mail:</div>\n' +
        '            <xsl:apply-templates select="//sipvs:mail"/>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:firstName">\n' +
        '        <div class="left value">\n' +
        '            <xsl:value-of select="."/>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:lastName">\n' +
        '        <div class="left value">\n' +
        '            <xsl:value-of select="."/>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:age">\n' +
        '        <div class="left value">\n' +
        '            <xsl:value-of select="."/>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '    <xsl:template match="sipvs:mail">\n' +
        '        <div class="left value">\n' +
        '            <xsl:value-of select="."/>\n' +
        '        </div>\n' +
        '    </xsl:template>\n' +
        '\n' +
        '</xsl:stylesheet>';
    var xslURI = "http://dis-major/dppo.xslt";
    var xml = '<?xml version="1.0" encoding="UTF-8" ?><team xmlns="http://www.example.org/sipvs" shortcut="sadsad"><user><firstName>sads&amp;&quot;&quot;xd</firstName><lastName>\'\'zxcx</lastName><age>4</age><mail>asd@sadsa</mail></user><name>sadsa</name><vip>false</vip><player>sad</player><player>&gt;&lt;</player><player>asd</player></team>';

    var obj = oXMLPlugin.CreateObject('objectId', 'Daňové priznanie', xml, xsd, 'http://www.example.org/sipvs', xsdURI, xsl, xslURI);
    var addObj = oXML.AddObject(obj);
    var res = oXML.Sign('signatureId', 'sha256', 'urn:oid:1.3.158.36061701.1.2.1');
    console.log(res);
}



function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}