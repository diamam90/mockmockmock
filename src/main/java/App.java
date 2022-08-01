import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.core.WireMockConfiguration;
import com.github.tomakehurst.wiremock.junit.WireMockRule;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;


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
        StubMapping soapReq=WireMock.stubFor(WireMock.get(WireMock.urlMatching("/soap/test"))
                .withHeader("Content-Type",WireMock.containing("xml"))
                .withRequestBody(WireMock.equalToXml("<header>body</header>")).willReturn(WireMock.okXml("<body>success</body>")));
        StubMapping soapFromFile = WireMock.stubFor(WireMock.get(WireMock.urlMatching("/soap/file"))
                .withHeader("Content-Type",WireMock.containing("xml"))
                .withRequestBody(WireMock.equalToXml("<header>body</header>")).willReturn(WireMock.ok().withBodyFile("test.xml")));
    }
}
