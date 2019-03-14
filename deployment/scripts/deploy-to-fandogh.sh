#!/bin/bash
fandogh service apply \
 -f ./deployment/fandogh-manifests/service-manifest.yaml \
 -p IMAGE_URL \
 -p TAG \
 -p SEC_NAME
