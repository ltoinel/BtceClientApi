 Java Client for BTC-e APIs
===========================

A robust and well documented Java client for BTC-e APIs (https://btc-e.com/api/documentation).

This client uses : 
* Jackson for JSON binding
* Logback for the logging
* Fluent-hc for the HTTP requests

### Binary

The jar file can be download directly on : 
https://github.com/ltoinel/BtceClientApi/blob/master/build/BtceClientApi-1.0.0-SNAPSHOT.jar

The library will be published in few days on a public maven repository.

### Features

Supports Public API : https://btc-e.com/api/3/
* Info
* Depth
* Ticker
* Trades

Supports Private API : https://btc-e.com/tapi/
* CancelOrder
* Trade
* ActiveOrders
* TradeHistory
* TransHistory
* GetInfo

### Todo list

[ ] Finalize the Unit tests


### Donate

If you like and use my Java client API, you can send me  0.001 LTC or BTC or more ;-)

    BTC: 1Ga1pTmLdUrHaqT51YYg5Y7bzcsuMVHEiD
    LTC: LYFZGZfbgPcXsyyn7v4wPXYBwnZebHRYsd
    
### Usage of BtceClientTradeApi

```java
// You can retrieve the following key and secret on https://btc-e.com/profile#api_keys
String key = "XXXXXXX-XXXXXXX-XXXXXXX-XXXXXXX-XXXXXXX";
String secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

// Instanciate a new client 
BtceClientTradeApi btceClientTradeApi = new BtceClientTradeApi(key,secret);

// Retrieve the account info
AccountInfo accountInfo = btceClientTradeApi.getAccountInfo();

// Make a trade
Transaction transaction = btceClientTradeApi.trade(Pair.ltc_eur, TransactionType.buy, 10, 0.1);

// Cancel an order
Transaction canceledTransaction = btceClientTradeApi.cancelOrder(orderId,Pair.ltc_eur);

// Get all active orders
Map<String,Order> orders = btceClientTradeApi.getActiveOrders();

// Cancel all the active orders
List<Transaction> transactions = btceClientTradeApi.cancelAllOrders();

....Etc
			
```

### Usage of BtceClientApi

```java
// Instanciate a new client 
BtceClientApi btceClientApi = new BtceClientApi();

// Get the ticker for the pair
Ticker ticker = btceClientApi.getTicker(Pair.btc_eur);

// Get the trades for the pair
List<Trade> trades = btceClientApi.getTrades(Pair.btc_eur);

// Get the depth for the pair
Depth depth = btceClientApi.getDepth(Pair.btc_eur);
		
```


### Legal

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.