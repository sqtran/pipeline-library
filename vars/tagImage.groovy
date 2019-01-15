#!/usr/bin/env groovy

class TagImageInput implements Serializable {
    String sourceImagePath =  ""
    String sourceImageName = ""
    String sourceImageTag = "latest"
    String toImagePath
    String toImageName
    String toImageTag

    TagImageInput init() {
        if(!toImageName?.trim()) toImageName = sourceImageName
        if(!toImageTag?.trim()) toImageTag = sourceImageTag
        return this
    }
}

def call(Map input) {
    call(new TagImageInput(input).init())
}

def call(TagImageInput input) {
    assert input.sourceImageName?.trim() : "Param sourceImageName should be defined."
    assert input.sourceImagePath?.trim() : "Param sourceImagePath should be defined."
    assert input.toImagePath?.trim() : "Param toImagePath should be defined."

    openshift.withCluster() {
        
        println "source = ${input.sourceImagePath}/${input.sourceImageName}:${input.sourceImageTag}"
        println "destination = ${input.toImagePath}/${input.toImageName}:${input.toImageTag}"
        
        openshift.tag("${input.sourceImagePath}/${input.sourceImageName}:${input.sourceImageTag}", 
                      "${input.toImagePath}/${input.toImageName}:${input.toImageTag}")
    }
}
