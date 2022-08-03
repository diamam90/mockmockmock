import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;
import org.xmlunit.diff.ComparisonType;


public class App {
    public static void main(String[] args) {
        WireMockServer server = new WireMockServer(WireMockConfiguration.options().port(8000).usingFilesUnderClasspath("src/main/resources/"));
        server.start();
        WireMock.configureFor(8000);
        StubMapping usersStub = WireMock.stubFor(WireMock.get(WireMock.urlEqualTo("/users"))
                .willReturn(WireMock.aResponse()
                        .withBody("Soon you will see our users here")
                        .withStatus(200)));
        StubMapping getUserStub = WireMock.stubFor(WireMock.get(WireMock.urlMatching("/users/[0-9]{1,5}"))
                .willReturn(WireMock.aResponse()
                        .withBody("Ivan ivanov")
                        .withStatus(200)));
        StubMapping soapReq = WireMock.stubFor(WireMock.get(WireMock.urlMatching("/soap/test"))
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.equalToXml("<header>body</header>")).willReturn(WireMock.okXml("<body>success</body>")));


//        StubMapping sogaz = WireMock.stubFor(WireMock.post("/sogaz")
//                .withHeader("Content-Type", WireMock.containing("xml"))
//                .withRequestBody(WireMock.equalToXml("<?xml version=\"1.0\"?>\n" +
//                        "<SOAP-ENV:Envelope xmlns:m0=\"http://www.integrator.sogaz.ru\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:SOAP-ENC=\"http://schemas.xmlsoap.org/soap/encoding/\" xmlns:SOAP-ENV=\"http://schemas.xmlsoap.org/soap/envelope/\">\n" +
//                        "    <SOAP-ENV:Body>\n" +
//                        "        <m:getContractsDetailsByNumber xmlns:m=\"http://www.perconalcabinet.sogaz.ru\">\n" +
//                        "            <m:ContractNumberList>\n" +
//                        "                <m0:ID>${xmlunit.ignore}</m0:ID>\n" +
//                        "             </m:ContractNumberList>\n" +
//                        "         </m:getContractsDetailsByNumber>\n" +
//                        "     </SOAP-ENV:Body>\n" +
//                        "</SOAP-ENV:Envelope>",true))
//                .willReturn(WireMock.ok().withBodyFile("getContractsDetailsByNumber.xml")));
    /*    StubMapping sogaz = WireMock.stubFor(WireMock.post("/sogaz")
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.matchingXPath("//getContractsDetailsByNumber/ContractNumberList/ID[text()='23']"))
                .willReturn(WireMock.ok().withBodyFile("getContractsDetailsByNumber.xml")));
      */
        StubMapping sogaz = WireMock.stubFor(WireMock.post("/sogaz")
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.matchingXPath("//getContractsDetailsByNumber/ContractNumberList/count[ID]=1"))
                .willReturn(WireMock.ok().withBodyFile("getContractsDetailsByNumber.xml")));

    }
}
