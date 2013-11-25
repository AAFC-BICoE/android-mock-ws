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

Starts web server on port 4567

URLs to try:

##Get all specimen replicates
```
http://localhost:4567/v1/specimenReplicate/

```

This should result in something like:

```
{

    "count": 5,
    "specimenReplicates": [
        "/v1/specimenReplicate/19249",
        "/v1/specimenReplicate/32564",
        "/v1/specimenReplicate/38816",
        "/v1/specimenReplicate/21147",
        "/v1/specimenReplicate/15040"
    ]

}
```
##Get ne specimen replicate
Now, use one of the above to get the full record:
```
http://localhost:4567/v1/specimenReplicate/19249

```
Should result in something like:
```
{
    "PK": 19249,
    "specimenIdentifier": 11871,
    "version": 3,
    "containerNumber": 7,
    "storageUnit": 2,
    "compartment": 1,
    "shelf": 1,
    "rack": 1
}
```

##Get count of specimen replicates
```
http://localhost:4567/v1/specimenReplicate/count/

```
Should result in something like:
```
{
    "type": "specimenReplicates",
    "count": 5
}