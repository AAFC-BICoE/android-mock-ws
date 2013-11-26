Build
======

```
mvn -DskipTests clean compile assembly:single
```

Run
====

```
export CLASSPATH=target/android-mock-ws-1.0-SNAPSHOT-jar-with-dependencies.jar
java ca.gc.agr.mbb.androidmockws.AndroidMockWS
```
Starts web server on port `4567`

**Note: needs java 1.7.x**


URLs to try
============

##Get all specimen replicate GET URIs
URL: http://localhost:4567/v1/specimenReplicate/

This should result in something like:

```
{

    "total": 9992,
    "limit": 100,
    "offset": 0,
    "type": "specimenReplicate",
    "nextPageUrl": "http://localhost:4567/v1/specimenReplicate?offset=100&limit=100",
    "uris": [
        "http://localhost:4567/v1/specimenReplicate/941756",
        "http://localhost:4567/v1/specimenReplicate/922332",
        "http://localhost:4567/v1/specimenReplicate/287714",
        "http://localhost:4567/v1/specimenReplicate/131839",
        "http://localhost:4567/v1/specimenReplicate/710279",
	"http://localhost:4567/v1/specimenReplicate/555856",
.
.
. [content removed]
.
.
        "http://localhost:4567/v1/specimenReplicate/271727",
        "http://localhost:4567/v1/specimenReplicate/645653"
    ]

}

```
##Get a specimen replicate record
Now, use one of the above to get the full record:
URL: http://localhost:4567/v1/specimenReplicate/19249

Should result in something like:
```
{
    "primaryKey": 154261,
    "name": "mbyfolp",
    "state": "xmbjptcnkuhv",
    "specimenIdentifier": 77596,
    "version": "zsi",
    "contents": "iliqhjyqorcn",
    "notes": "qrkhfmyqyemch tltg z",
    "storageMedium": "minm sg t owioggbbuf",
    "startDate": "2010-01-05 10:02:12",
    "revivialDate": "2014-08-01 01:54:12",
    "dateDestroyed": "2010-07-18 04:46:12",
    "parent": "http://localhost:4567/v1/specimenReplicate/764673",
    "location": {
        "containerNumber": 7,
        "storageUnit": 1,
        "compartment": 2,
        "shelf": 3,
        "rack": 1,
        "dateMoved": "2010-05-12 22:26:12",
        "wellColumn": 1,
        "wellRow": "f"
    }
}
```

##Get count of specimen replicates
URL: http://localhost:4567/v1/specimenReplicate/count/


Should result in something like:
```
{
    "type": "specimenReplicates",
    "count": 5
}
```

TODO
=====
0. Move to Jersey from Spark
1. Command line set port number
2. POST/PUT for specimen replicate
3. Add version to json returned
4. implement one or two other seqdb objects