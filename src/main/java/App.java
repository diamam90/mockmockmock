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


        StubMapping checkContractId23 = WireMock.stubFor(WireMock.post("/sogaz")
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.matchingXPath("//getContractsDetailsByNumber/ContractNumberList/ID[text()='23']"))
                .willReturn(WireMock.ok().withBodyFile("getContractsDetailsByNumber.xml")));

        StubMapping checkContractNumberList = WireMock.stubFor(WireMock.post("/sogaz")
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.matchingXPath("//getContractsDetailsByNumber/ContractNumberList/"))
                .willReturn(WireMock.ok().withBodyFile("getContractsDetailsByNumber.xml")));
        StubMapping countIdStub = WireMock.stubFor(WireMock.post("/test2")
                .withHeader("Content-Type", WireMock.containing("xml"))
                .withRequestBody(WireMock.matchingXPath("//getContractsDetailsByNumber/ContractNumberList[count(ID)=2]"))
                .willReturn(WireMock.ok().withBodyFile("getContractsDetailsByNumber.xml")));

    }
}
