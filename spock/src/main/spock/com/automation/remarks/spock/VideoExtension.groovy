package com.automation.remarks.spock

import org.spockframework.runtime.extension.IGlobalExtension
import org.spockframework.runtime.model.SpecInfo

/**
 * Created by sepi on 21.10.16.
 */
class VideoExtension implements IGlobalExtension{
    @Override
    void start() {

    }

    @Override
    void visitSpec(SpecInfo spec) {
        spec.addListener(new VideoListener())
    }

    @Override
    void stop() {

    }
}
