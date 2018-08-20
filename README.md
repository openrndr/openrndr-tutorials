# OPENRNDR examples

This repository contains examples that accompany the OPENRNDR guide.

## Running the examples

The advised way of running the examples is to create a new project from existing sources in IntelliJ. Examples can be launched by right-clicking on the widget next to the main() functions. Make sure to set the working directory to the root of the tutorials for the `data` folder to be found.

Alternatively you can run the samples from the command line using `./gradlew :<project-name>:run`

Note that on OSX an additional step is required. The JVM argument `-XrunOnFirstThread` has to be added in the run configuration. When running the examples from the command line this has been taken care of. 
