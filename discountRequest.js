const puppeteer = require('puppeteer');




async function run() {
	discountRequest(408354, 8)
}
run();



async function CalcAndPrintDisc(htmlString){
	//retrieving price after discount
			console.log(htmlString);

		s = "<span class='productPrice'>";
		let price = null;
		if(htmlString.includes(s)){
		index = htmlString.indexOf(s) + s.length; // end of first occurance of s (beginning of pd price)
		offset = htmlString.substring(index).indexOf(' '); // product's price length
		price = htmlString.substring(index, index + offset);
		}
		console.log("price after discount = " + price);
		return price;
}
async function discountRequest(prodID,qty){
    let browser = await puppeteer.launch({headless: false});
    let page = await browser.newPage();
    await page.setRequestInterception(true); //set the request option (triggered with goto)
    page.on('request', req => {
        //create data
        let remarks = '';
        let unitofmeasure = '';
        var request = {
            AjaxCallAction: "AddProductToBasket",
            paramProductID: prodID,
            paramQuantity: qty,
            paramRemarks: remarks,
            paramUM: unitofmeasure
        }
        // convert JSON to x-www-form-urlencoded
        let reqBody = Object.keys(request).map((k) => {
            return encodeURIComponent(k) + '=' + encodeURIComponent(request[k])
        }).join('&');
        var data = {
            'method': 'POST',
            'url': 'https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx',
             // the custom headers
            'headers': {
                'Content-Type': 'application/x-www-form-urlencoded; charset=UTF-8',
                'X-Requested-With': 'XMLHttpRequest'
            },
            'postData': reqBody
        };
		console.log("Request Headers: " + request.headers);
		console.log("request: " + request);
        req.continue(data);
    });
    // capture intercepted response
    page.on('response', async response => {
        console.log("Resource Type: "  + response.request().resourceType());
		console.log("Response Headers: " + response.headers);
		console.log("Response: " + response);
		res = await response.text();
		price = CalcAndPrintDisc(res);
        //console.log(res + "\n Price after discount: " + price);
        console.log("==============");
    });

    const response = await page.goto('https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx');
    // only capture default response
    //console.log(response.statusText());
    //console.log(response.responseText);

    //console.log('done');
	
	
}

