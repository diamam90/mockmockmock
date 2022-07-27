import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.github.tomakehurst.wiremock.stubbing.StubMapping;

public class App {
    public static void main(String[] args) {
        WireMockServer server = new WireMockServer(8000);
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
        server.addStubMapping(usersStub);
        server.addStubMapping(getUserStub);

    }
}
