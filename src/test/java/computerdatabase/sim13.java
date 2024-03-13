package computerdatabase;

import static io.gatling.javaapi.core.CoreDsl.*;
import static io.gatling.javaapi.http.HttpDsl.*;

import io.gatling.javaapi.core.*;
import io.gatling.javaapi.http.*;

import java.time.Duration;

/**
 * This sample is based on our official tutorials:
 * <ul>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/quickstart">Gatling quickstart tutorial</a>
 *   <li><a href="https://gatling.io/docs/gatling/tutorials/advanced">Gatling advanced tutorial</a>
 * </ul>
 */
public class sim13 extends Simulation {

    private HttpProtocolBuilder httpProtocol = http
            .baseUrl("http://localhost:8082")
            .inferHtmlResources(AllowList(), DenyList(".*\\.js", ".*\\.css", ".*\\.gif", ".*\\.jpeg", ".*\\.jpg", ".*\\.ico", ".*\\.woff", ".*\\.woff2", ".*\\.(t|o)tf", ".*\\.png", ".*\\.svg", ".*detectportal\\.firefox\\.com.*"))
            .acceptHeader("*/*")
            .acceptEncodingHeader("gzip, deflate, br")
            .contentTypeHeader("application/json")
            .userAgentHeader("PostmanRuntime/7.37.0");


    ChainBuilder script_1=
            exec(http("request_0")
            .post("/weather-forecast/forecast/by-city-id")
            .body(RawFileBody("computerdatabase.sim13/0000_request.json")),
            pause(15),
            http("request_1")
                    .post("/weather-forecast/forecast/by-city-id")
                    .body(RawFileBody("computerdatabase.sim13/0001_request.json")),
            pause(12),
            http("request_2")
                    .post("/weather-forecast/forecast/by-city-id")
                    .body(RawFileBody("computerdatabase.sim13/0002_request.json")),
            pause(8),
            http("request_3")
                    .post("/weather-forecast/forecast/by-city-id")
                    .body(RawFileBody("computerdatabase.sim13/0003_request.json")),
            pause(2)
        );

    ChainBuilder script_2=
            exec(
                    http("request_0")
                            .post("/weather-forecast/forecast/by-city-id")
                            .body(RawFileBody("computerdatabase.sim13/0000_request.json")),
                    pause(2),
                    http("request_1")
                            .post("/weather-forecast/forecast/by-city-id")
                            .body(RawFileBody("computerdatabase.sim13/0001_request.json")),
                    pause(4),
                    http("request_2")
                            .post("/weather-forecast/forecast/by-city-id")
                            .body(RawFileBody("computerdatabase.sim13/0002_request.json")),
                    pause(6),
                    http("request_3")
                            .post("/weather-forecast/forecast/by-city-id")
                            .body(RawFileBody("computerdatabase.sim13/0003_request.json")),
                    pause(3)
            );



    ScenarioBuilder users1 = scenario("Users1").exec(script_1);
    ScenarioBuilder users2 = scenario("Users2").exec(script_2);

    int nbUsers = Integer.getInteger("users", 1);
    long myRamp = Long.getLong("duration", 0);

    int pause = Integer.getInteger("repos",8);
    {
        setUp(
            users1
                .injectOpen(rampUsers(nbUsers).during(Duration.ofMinutes(myRamp))),
            users2
                .injectOpen(rampUsers(nbUsers).during(Duration.ofMinutes(myRamp)))
        )
        .uniformPauses(Duration.ofSeconds(pause))
        .protocols(httpProtocol);
    }
}
