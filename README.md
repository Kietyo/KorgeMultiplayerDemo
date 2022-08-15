# Korge Multiplayer Demo

## Background

This is a demo project showcasing how a Korge client can be used in conjunction with a Ktor websocket server. The
multiplayer demo is very simple. When a user is connected to a server, they're assigned a circle that they can move
around. The circle can be moved with WASD keys. All other clients connected to the server should be able to see you as
you move your circle around.

## Try it now!

https://kietyo.github.io/

Note: You can open it up in multiple tabs to create multiple clients.

## Video Demo

https://youtu.be/ubNeTW8fhoo

## Project Directory Structure

**Server:** Contains server code. Built using the Ktor framework.

**KorgeClient:** Contains client code for connecting to the server. Built using the Korge framework.

**GameLogic:** The term `GameLogic` is a misnomer, this is more like a bunch of shared files that're used between
the `Server` and `KorgeClient` code.

## How to deploy locally

1. Run the server by running `KorgeMultiplayer -> Tasks -> application -> run` in the IntelliJ Gradle panel.
2. Run the korge client by running `KorgeMultiplayer -> Tasks -> run -> runJvm`.
   1. Note: If you want to connect to the cloud hosted server, set `IS_LOCAL_DEPLOYMENT = false`.

## How to deploy on gcloud (at your own risk)

_Note: This is at your own risk because I got it working for myself, but it took a lot of debugging and I didn't record
how I got gcloud setup._

1. You may need to modify `Server/build.gradle.kts` with your gcloud project names first.
2. Run `KorgeMultiplayer -> Tasks -> jib -> jibDockerBuild`
3. If you've setup your gcloud correctly, the image should be available in the `Container Registry`. You can deploy it
   by using the gcloud GUI.
4. Update the websocket settings in the Korge client to point to the URL of your server. Follow documentation in the
   korge client main code for how to setup the connection.

## Resources used for learning
- https://ryanharter.com/blog/2020/12/running-ktor-web-services-on-cloud-run/
- https://github.com/rollie42/draw-what-now
- https://ktor.io/docs/welcome.html
- https://ktor.io/docs/creating-web-socket-chat.html
- https://ktor.io/docs/websocket.html
