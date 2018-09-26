# Artifactory Docker Info User Plugin

Allows REST access to the docker image information. This plugin exposes one
execution:

## dockerInfo

`dockerInfo` returns a JSON representation of the current info of a given docker image.

The parameters of the request are as follows:

- `dockerImage`: The image name to be queried
- `dockerTag`: The image tag to be queried (default: latest)
- `dockerRepo`: The docker repo where the image is stored (default: docker-local)

The returned JSON string has the following fields:

- `sha256`: The sha256 hash of the docker image.
- `digest`: The digest of the docker image.
- `tag`: The tag of the docker image.

For example:

```
$ curl -u admin:password 'http://localhost:8081/artifactory/api/plugins/execute/dockerInfo?params=dockerImage=myPath/myImage|dockerTag=publish|dockerRepo=docker-custom
{
    "sha256": "asdfghjkl",
    "digest": "sha256:asdfghjkl",
    "tag": "publish"
}
```

## Deployment

Copy `dockerInfo.groovy` to `${ARTIFACTORY_HOME}/etc/plugins` directory. Then reload your plugins (see [Reloading Plugins](https://www.jfrog.com/confluence/display/RTF/User+Plugins#UserPlugins-ReloadingPlugins))

## Contributing

Please read [CONTRIBUTING.md](CONTRIBUTING.md) for details on our code of conduct, and the process for submitting pull requests to us.

## Authors

* **Peter Kreidermacher** - [HealthPartners](https://github.com/healthpartners)

See also the list of [contributors](https://github.com/healthpartners/artifactory-docker-info-plugin/contributors) who participated in this project.

## License

This project is licensed under the MIT License - see the [LICENSE.md](LICENSE.md) file for details
