# I have no idea why this works.... keep the eval and load separate!
mit-scheme  --eval '(cd "sicm/scmutils/src")' '(load "load.scm")' '(cd "../../..")' --load test