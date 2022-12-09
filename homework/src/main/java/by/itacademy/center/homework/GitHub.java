package by.itacademy.center.homework;

interface GitHub {

    void openPullRequest(String personalAccessToken, String repositoryOwner, String repositoryName, Payload payload);

    record Payload(String title, String body, String head, String base) {}
}
