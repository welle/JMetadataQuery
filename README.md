# JMetadataQuery [![Build Status](https://travis-ci.org/welle/JMetadataQuery.svg?branch=master)](https://travis-ci.org/welle/JMetadataQuery) [![quality gate](https://sonarcloud.io/api/project_badges/measure?project=aka.jmetadataquery%3AJMetadataQuery&metric=alert_status)](https://sonarcloud.io/dashboard?id=aka.jmetadataquery%3AJMetadataQuery) #

## Quick summary ##
JMetadataQuery is Java library providing query search for Mediainfo metadata.

## How to use it ##
Instantiate a search, and call `isMatchingFile` method.
i.e.:  
`AudioCompressionModeSearch audioCompressionModeSearch = new AudioCompressionModeSearch(BinaryCondition.Op.NOT_EQUAL_TO, CompressionMode.LOSSLESS;` 
`boolean result = audioCompressionModeSearch.isFileMatchingCriteria(file);`  

See Unit tests to see how it works.
You can fork and implementing other searches.

### Version

Go to [my maven repository](https://github.com/welle/maven-repository) to get the latest version.

## Notes
Need the eclipse-external-annotations-m2e-plugin: 

p2 update site to install this from: http://www.lastnpe.org/eclipse-external-annotations-m2e-plugin-p2-site/ (The 404 is normal, just because there is no index.html; it will work in Eclipse.)
