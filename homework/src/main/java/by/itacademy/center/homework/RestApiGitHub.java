package by.itacademy.center.homework;

import com.google.gson.Gson;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

final class RestApiGitHub implements GitHub {

    private final HttpClient client = HttpClient.newHttpClient();
    private final Gson gson = new Gson();

    @Override
    public void openPullRequest(
            String personalAccessToken,
            String repositoryOwner,
            String repositoryName,
            Payload payload
    ) {
        try {
            var response = client.send(
                    request(personalAccessToken, repositoryOwner, repositoryName, payload),
                    HttpResponse.BodyHandlers.ofString()
            );
            if (response.statusCode() != 201) {
                throw new CouldNotOpenPullRequest(response.body());
            }
        } catch (IOException e) {
            throw new CouldNotOpenPullRequest(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new CouldNotOpenPullRequest(e);
        }
    }

    private HttpRequest request(
            String personalAccessToken,
            String repositoryOwner,
            String repositoryName,
            Payload payload
    ) {
        return HttpRequest.newBuilder()
                .header("Accept", "application/vnd.github+json")
                .header("Authorization", "Bearer " + personalAccessToken)
                .header("X-GitHub-Api-Version", "2022-11-28")
                .uri(uri(repositoryOwner, repositoryName))
                .POST(HttpRequest.BodyPublishers.ofString(gson.toJson(payload)))
                .build();
    }

    private URI uri(String repositoryOwner, String repositoryName) {
        return URI.create("https://api.github.com/repos/" + repositoryOwner + '/' + repositoryName + "/pulls");
    }

    private static final class CouldNotOpenPullRequest extends RuntimeException {

        CouldNotOpenPullRequest(Throwable cause) {
            super(cause);
        }

        CouldNotOpenPullRequest(String message) {
            super(message);
        }
    }
}
