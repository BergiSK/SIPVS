function signTheForm(){
    var xml = httpGet('http://localhost:8080/xml');
    var xsd = httpGet('http://localhost:8080/xsd');
    var xsl = httpGet('http://localhost:8080/xsl');

    var oXML = new ActiveXObject("DSig.XadesSigAtl");
    var oXMLPlugin = new ActiveXObject("DSig.XmlPluginAtl");
    var namespace = 'http://www.example.org/sipvs';
    var documentName = 'Registration form';
    var xsdURI = "http://sipvs.xsd";
    var xslURI = "http://sipvs.xslt";

    var obj = oXMLPlugin.CreateObject('objectId', documentName, xml, xsd, namespace, xsdURI, xsl, xslURI);
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