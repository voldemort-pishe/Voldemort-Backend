#!/bin/bash
fandogh service apply \
 -f ./deployment/fandogh-manifests/service-manifest.yaml \
 -p registry.gitlab.com/voldemortapp/voldemort-backend \
 -p latest \
 -p gitlab-cred
