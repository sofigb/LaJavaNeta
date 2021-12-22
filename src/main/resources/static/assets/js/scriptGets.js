// https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false

$(document).ready(function() {
    prueba();
  });
  

async function prueba() {

    const request = await fetch('https://api.coingecko.com/api/v3/coins/markets?vs_currency=usd&order=market_cap_desc&per_page=100&page=1&sparkline=false', {
        method: 'GET',
        headers: {
          'Accept': 'application/json',
          'Content-Type': 'application/json'
        },
      });
      const coins = await request.json();
    
    let listaCoins = '';
        
    for (let coin of coins) {
        
        let coinHTML = '<tr> <td>'+ '<img src="' + coin.image +'" alt="" style="width:2rem"> </td> <td> '+ coin.id +' </td> <td>'+ coin.symbol +'</td> <td>'+ coin.name +'</td>  <td>'+ coin.current_price +'</td> <td> '+ coin.total_volume +' </td> <td> '+ coin.high_24h+' </td> <td> '+ coin.low_24h +' </td> <td> '+ coin.price_change_24h +' </td> </tr> ';
        listaCoins += coinHTML;
    }
    console.log(coins);    
    document.querySelector('#tablaCoins tbody').outerHTML = listaCoins;
   
}




