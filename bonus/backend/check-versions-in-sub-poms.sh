#!/bin/sh

if grep -R --include="pom.xml" "<version>[0-9]" | grep -v "^pom"; then
  echo "FAIL: do not hard-code versions in sub-poms. Remove the <version> tag and put it into the root pom's dependencyManagement"
  exit 1
else
  exit 0
fi
