# JMetadataQuery [![Build Status](https://travis-ci.org/welle/JMetadataQuery.svg?branch=master)](https://travis-ci.org/welle/JMetadataQuery) [![Quality Gate](https://sonarcloud.io/api/badges/gate?key=aka.jmetadataquery:JMetaDataQuery)](https://sonarcloud.io/dashboard/index/aka.jmetadataquery:JMetaDataQuery) #

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
