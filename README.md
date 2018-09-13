# MUPLibrary

## Sample Project

For more information how to use the library in Java checkout [Sample App](https://github.com/prajwaldcunha/MUPLibrary/tree/master/app) in repository.


## Add this to your project's `build.gradle`

```groovy
allprojects {
    repositories {
        maven { url "https://jitpack.io" }        
        maven { url "http://dl.bintray.com/lukaville/maven" }
    }
}
```

And add this to your module's `build.gradle` 

```groovy
dependencies {
	implementation 'com.github.prajwal:MUPLibrary:x.y.z'
}
```

change `x.y.z` to version in the [release page](https://github.com/prajwaldcunha/MUPLibrary/releases)

