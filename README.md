BtceClientApi
=============

A great Java client for BTC-e API 

Please donate
-------------
* Bitcoin : 1Ga1pTmLdUrHaqT51YYg5Y7bzcsuMVHEiD
* Litecoin : LYFZGZfbgPcXsyyn7v4wPXYBwnZebHRYsd

Features
-------- 
* Supports Public API : https://btc-e.com/api/3/
* Supports Private API : https://btc-e.com/tapi/

Sample of use
-------------

```java
String key = "XXXXXXX-XXXXXXX-XXXXXXX-XXXXXXX-XXXXXXX";
String secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

// Create a new client 
BtceClientApi btceClientApi = new BtceClientApi(key,secret);

// Retrieve the account info
AccountInfo accountInfo = btceClientApi.getAccountInfo();
System.out.println("Amout of Euros:" + accountInfo.getFunds().getEur());
```