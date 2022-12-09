package by.itacademy.center.homework;

final class FakeGitHub implements GitHub {

    private PullRequest pullRequest = null;

    @Override
    public void openPullRequest(
            String personalAccessToken,
            String repositoryOwner,
            String repositoryName,
            Payload payload
    ) {
        pullRequest = new PullRequest(personalAccessToken, repositoryOwner, repositoryName, payload);
    }

    PullRequest pullRequest() {
        return pullRequest;
    }

    record PullRequest(String personalAccessToken, String repositoryOwner, String repositoryName, Payload payload) {}
}
