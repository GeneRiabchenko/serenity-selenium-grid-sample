# Serenity BDD + Selenium WebDriver + Selenium Grid sample

Acceptance tests for a small React TodoMVC application, written in Java with
[Serenity BDD](https://serenity-bdd.github.io/) and Selenium WebDriver. The
application under test and a Selenium Grid (hub + Chrome/Firefox nodes) are
started locally with Docker Compose, and tests run against the Grid in
parallel via JUnit 5.

No Playwright is used anywhere in this project — browser automation is done
purely through Selenium WebDriver's `RemoteWebDriver`.

## Stack

- Java 17
- Serenity BDD 4 (`serenity-core`, `serenity-junit5`)
- JUnit 5 (parallel execution enabled)
- Selenium WebDriver (`RemoteWebDriver` against Selenium Grid)
- Docker Compose: React (Vite) TodoMVC app + `selenium/hub` + `selenium/node-chrome` + `selenium/node-firefox`

## Project layout

```
app/                    React (Vite) TodoMVC application + Dockerfile
docker-compose.yml       App + Selenium Grid (hub, chrome, firefox)
src/test/java/...        Serenity page object + JUnit 5 acceptance tests
src/test/resources/      serenity.conf, junit-platform.properties
```

## Running locally

1. Start the application and the Grid:

   ```bash
   docker compose up -d --build
   ```

   Optionally scale up nodes for more parallelism:

   ```bash
   docker compose up -d --build --scale chrome=3 --scale firefox=2
   ```

   The Grid console is available at http://localhost:4444/ui and the app at
   http://localhost:3000. Note that the app is reached by the browser nodes
   through the Docker network as `http://app` (its Compose service name), not
   `localhost` — that's what `webdriver.base.url` in `serenity.conf` points to.

2. Run the tests:

   ```bash
   mvn clean verify
   ```

   Tests run in parallel (see `junit-platform.properties`), and the hub
   distributes sessions across whichever Chrome/Firefox nodes are available.

3. Open the Serenity report:

   ```
   target/site/serenity/index.html
   ```

4. Tear down the stack:

   ```bash
   docker compose down -v
   ```

## Configuration

- `src/test/resources/serenity.conf` — WebDriver points at the Grid hub
  (`webdriver.remote.url = http://localhost:4444/wd/hub`) and defaults to
  Chrome (`webdriver.remote.driver = chrome`). Override the browser without
  editing the file, e.g.:

  ```bash
  mvn clean verify -Dwebdriver.remote.driver=firefox
  ```

- `src/test/resources/junit-platform.properties` — controls how many tests
  run concurrently (`junit.jupiter.execution.parallel.config.fixed.parallelism`).
  Match this to the number of Grid node slots you scaled up.

## CI

`.github/workflows/tests.yml` starts the same Docker Compose stack on GitHub
Actions, waits for the Grid to report ready, runs `mvn verify`, and uploads
the Serenity HTML report as a build artifact.
