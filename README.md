# Deltares OSS
Liferay workspace that builds up oss.deltares.nl

# Requirements
- OpenJDK8
- Gradle 4.10.2

# Setup
After forking and checking out, make a copy of `gradle.properties` and name it `gradle-local.properties`, use this new file for any custom properties you need, so as to avoid committing sensitive information.

When developing for Liferay DXP, the following properties need to be filled with your Liferay.com login credentials:

```
liferay.workspace.bundle.token.email.address=
liferay.workspace.bundle.token.password=
```

The `liferay.workspace.bundle.token.download` property should then be set to `true`.

The current `gradle.properties` file is pointing to the latest (at the point of this writing) CE bundle.
It also has a commented DXP bundle above it, in case getting a development license is possible.

Once all the necessary properties have been filled out, run the following command to download the bundle:

```
./gradlew initBundle
```

Once downloaded, the following command will start the portal, you are expected to have [Liferay blade](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/blade-cli) installed:

```
blade server start
```

If you do not wish to use Blade, you can also start the server with tomcat's `startup.sh` or `startup.bat` script within the `bundles/tomcat-x/bin/` folder.

# Useful links
- [Liferay documentation index](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/introduction-to-liferay-development)
- [Liferay portlet documentation](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/portlets)
- [Liferay service builder documentation](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/service-builder)
- [Liferay web services documentation](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/web-services)
- [Liferay theme documentation](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/themes-and-layout-templates)
- [Liferay JavaScript API documentation](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/liferay-javascript-apis)
- [Liferay Taglib documentation](https://portal.liferay.dev/docs/7-1/tutorials/-/knowledge_base/t/front-end-taglibs)
