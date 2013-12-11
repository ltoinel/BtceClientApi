BtceClientApi
=============

A great Java client for BTC-e API (https://btc-e.com/api/documentation).

### Features

* Supports Public API : https://btc-e.com/api/3/
** depth
** ticker
** trades

* Supports Private API : https://btc-e.com/tapi/
** CancelOrder
** Trade
** ActiveOrders
** TradeHistory
** TransHistory
** getInfo

### Donate

If you like an use my Java client API : 

    BTC: 1Ga1pTmLdUrHaqT51YYg5Y7bzcsuMVHEiD
    LTC: LYFZGZfbgPcXsyyn7v4wPXYBwnZebHRYsd
    
### Usage

```java
String key = "XXXXXXX-XXXXXXX-XXXXXXX-XXXXXXX-XXXXXXX";
String secret = "XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX";

// Create a new client 
BtceClientApi btceClientApi = new BtceClientApi(key,secret);

// Retrieve the account info
AccountInfo accountInfo = btceClientApi.getAccountInfo();
System.out.println("Amout of Euros:" + accountInfo.getFunds().getEur());

// Make a trade
Transaction transaction = btceClientApi.trade(Pair.ltc_eur, TransactionType.buy, 10, 0.1);
long orderId = transaction.getOrder_id();
System.out.println("Transaction Order Id:" + orderId);

// Cancel an order
Transaction canceledTransaction = btceClientApi.cancelOrder(orderId,Pair.ltc_eur);
System.out.println("Canceled Transaction Order Id:" + canceledTransaction.getOrder_id());

// Get all active orders
Map<String,Order> orders = btceClientApi.getActiveOrders();
System.out.println("Number of active orders : " + orders.size());

// Cancel all the active orders
List<Transaction> transactions = btceClientApi.cancelAllOrders();
System.out.println("Number of orders canceled: " + transactions.size());

....Etc
			
```

### Legal

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.