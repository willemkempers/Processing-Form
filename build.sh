#!/bin/bash
javac -cp /Applications/Processing.app/Contents/Java/core.jar -d . src/*.java
jar cvf library/form.jar Form/Form.class
