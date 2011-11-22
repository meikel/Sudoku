@echo off

mvn install:install-file ^
  -Dfile=JaCoP-3.1.2.jar ^
  -DgroupId=jacop ^
  -DartifactId=jacop ^
  -Dversion=3.1.2 ^
  -Dpackaging=jar ^
  -DgeneratePom=true ^
  -DcreateChecksum=true
