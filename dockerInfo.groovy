import org.artifactory.repo.*
import groovy.json.JsonBuilder

executions {

    dockerInfo(httpMethod: 'GET', users: ['anonymous'], groups: ['readers']) { params ->
        def dockerImage = params?.get('dockerImage')?.get(0) as String
        def dockerTag = params?.get('dockerTag')?.get(0) as String ?: 'latest'
        def dockerRepo = params?.get('dockerRepo')?.get(0) as String ?: 'docker-local'
        def imagePath = createTagPath(dockerImage, dockerTag)
        def rpf = new RepoPathFactory()
        sourcePath = rpf.create(dockerRepo, imagePath + "manifest.json")
        if (!repositories.exists(sourcePath)) {
            message = "sourceTag: " + sourcePath.toPath() + " does not exist!"
            log.error message
            status = 422
            return
        }
        if (sourcePath.isFolder()) {
            message = "Image path: " + sourcePath.toPath() + " from " + imagePath + " is invalid!"
            log.error message
            status = 422
            return
        }
        if (repositories.exists(sourcePath)) {
            def json = [
                    sha256  : repositories.getProperty(sourcePath, "sha256"),
                    digest  : repositories.getProperty(sourcePath, "docker.manifest.digest"),
                    tag     : repositories.getProperty(sourcePath, "docker.manifest"),
            ]
            message = new JsonBuilder(json).toPrettyString()
            status = 200
        }
    }
}

private String createTagPath(String dockerImage, String dockerTag) {
    return dockerImage + "/" + dockerTag + "/"
}
