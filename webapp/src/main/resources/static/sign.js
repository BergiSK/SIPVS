function signTheForm(){
    var xml = httpGet('http://localhost:8080/xml');
    var xsd = httpGet('http://localhost:8080/xsd');
    var xsl = httpGet('http://localhost:8080/xsl');

    var oXML = new ActiveXObject("DSig.XadesSigAtl");
    var oXMLPlugin = new ActiveXObject("DSig.XmlPluginAtl");
    var namespace = 'http://www.example.org/sipvs';
    var documentName = 'Registration form';
    var documentName2 = 'Registration form2';
    var xsdURI = "http://sipvs.xsd";
    var xslURI = "http://sipvs.xslt";

    var obj = oXMLPlugin.CreateObject2('objectId', documentName, xml, xsd, namespace, xsdURI, xsl, xslURI, "HTML");
    var obj2 = oXMLPlugin.CreateObject2('objectId2', documentName2, xml, xsd, namespace, xsdURI, xsl, xslURI, "HTML");
    var addObj = oXML.AddObject(obj);
    var addObj2 = oXML.AddObject(obj2);
    var res = oXML.Sign('signatureId', 'sha256', 'urn:oid:1.3.158.36061701.1.2.1');
    var signedDocument = oXML.SignedXmlWithEnvelope(); //base to vratit

    httpPost('http://localhost:8080/saveSigned', signedDocument);
}

function httpGet(theUrl)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "GET", theUrl, false ); // false for synchronous request
    xmlHttp.send( null );
    return xmlHttp.responseText;
}

function httpPost(theUrl, data)
{
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open( "POST", theUrl, false ); // false for synchronous request
    xmlHttp.send( data );
    return xmlHttp.responseText;
}