const puppeteer = require('puppeteer');

async function run() {
    let browser = await puppeteer.launch({headless: false});
    let page = await browser.newPage();
    await page.setRequestInterception(true); //set the request option (triggered with goto)
    page.on('request', req => {
        //create data
        let prodID = 7296073231578;
        let qty = 2;
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
        req.continue(data);
    });
    // capture intercepted response
    page.on('response', async response => {
        console.log("Resource Type: "  + response.request().resourceType());
        console.log("Response Text: " + await response.text());
        console.log("==============");
    });

    const response = await page.goto('https://www.shufersal.co.il/_layouts/Shufersal_Pages/ajax.aspx');
    // only capture default response
    //console.log(response.statusText());
    //console.log(response.responseText);

    //console.log('done');

}

run();