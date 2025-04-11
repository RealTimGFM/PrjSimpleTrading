<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ page import="com.example.simpletradingapp.model.StockDataset" %>
<html>
<head>
    <title>Graph</title>
    <style>
        #index-chart {
            max-width: 650px;
        }
    </style>
    <script>
        var apiData = [
            {
                "upd_Date": "2023-01-06T00:00:00",
                "price": 2537.25,
                "volume": 113594.0,
                "Open": 2529.0,
                "High": 2547.5,
                "Low": 2518.65
            },
            {
                "upd_Date": "2023-01-09T00:00:00",
                "price": 2596.55,
                "volume": 298492.0,
                "Open": 2543.7,
                "High": 2601.8,
                "Low": 2539.1
            },
            {
                "upd_Date": "2023-01-10T00:00:00",
                "price": 2557.95,
                "volume": 152642.0,
                "Open": 2604.05,
                "High": 2605.0,
                "Low": 2546.05
            },
            {
                "upd_Date": "2023-01-11T00:00:00",
                "price": 2525.5,
                "volume": 127795.0,
                "Open": 2555.2,
                "High": 2558.2,
                "Low": 2522.0
            },
            {
                "upd_Date": "2023-01-12T00:00:00",
                "price": 2472.1,
                "volume": 185343.0,
                "Open": 2525.0,
                "High": 2531.9,
                "Low": 2465.65
            },
            {
                "upd_Date": "2023-01-13T00:00:00",
                "price": 2467.35,
                "volume": 248982.0,
                "Open": 2468.6,
                "High": 2473.2,
                "Low": 2434.8
            },
            {
                "upd_Date": "2023-01-16T00:00:00",
                "price": 2444.7,
                "volume": 140006.0,
                "Open": 2474.9,
                "High": 2479.75,
                "Low": 2436.05
            },
            {
                "upd_Date": "2023-01-17T00:00:00",
                "price": 2478.55,
                "volume": 186970.0,
                "Open": 2453.15,
                "High": 2483.5,
                "Low": 2452.0
            },
            {
                "upd_Date": "2023-01-18T00:00:00",
                "price": 2474.6,
                "volume": 126814.0,
                "Open": 2479.0,
                "High": 2491.5,
                "Low": 2460.45
            },
            {
                "upd_Date": "2023-01-19T00:00:00",
                "price": 2471.1,
                "volume": 201558.0,
                "Open": 2474.2,
                "High": 2480.95,
                "Low": 2457.5
            },
            {
                "upd_Date": "2023-01-20T00:00:00",
                "price": 2442.7,
                "volume": 174012.0,
                "Open": 2481.7,
                "High": 2481.7,
                "Low": 2438.1
            },
            {
                "upd_Date": "2023-01-23T00:00:00",
                "price": 2429.6,
                "volume": 213860.0,
                "Open": 2450.0,
                "High": 2466.0,
                "Low": 2426.0
            },
            {
                "upd_Date": "2023-01-24T00:00:00",
                "price": 2415.15,
                "volume": 349349.0,
                "Open": 2432.5,
                "High": 2443.9,
                "Low": 2388.65
            },
            {
                "upd_Date": "2023-01-25T00:00:00",
                "price": 2382.95,
                "volume": 103914.0,
                "Open": 2410.0,
                "High": 2414.8,
                "Low": 2381.0
            },
            {
                "upd_Date": "2023-01-27T00:00:00",
                "price": 2337.75,
                "volume": 452582.0,
                "Open": 2381.95,
                "High": 2388.05,
                "Low": 2312.1
            },
            {
                "upd_Date": "2023-01-30T00:00:00",
                "price": 2351.3,
                "volume": 1392194.0,
                "Open": 2337.75,
                "High": 2369.4,
                "Low": 2301.15
            },
            {
                "upd_Date": "2023-01-31T00:00:00",
                "price": 2353.9,
                "volume": 100296.0,
                "Open": 2385.0,
                "High": 2386.1,
                "Low": 2341.7
            },
            {
                "upd_Date": "2023-02-01T00:00:00",
                "price": 2338.05,
                "volume": 573669.0,
                "Open": 2380.0,
                "High": 2380.9,
                "Low": 2305.0
            },
            {
                "upd_Date": "2023-02-02T00:00:00",
                "price": 2326.8,
                "volume": 104181.0,
                "Open": 2325.0,
                "High": 2347.7,
                "Low": 2311.35
            },
            {
                "upd_Date": "2023-02-03T00:00:00",
                "price": 2329.05,
                "volume": 148804.0,
                "Open": 2345.0,
                "High": 2347.9,
                "Low": 2293.2
            },
            {
                "upd_Date": "2023-02-06T00:00:00",
                "price": 2311.5,
                "volume": 206263.0,
                "Open": 2325.0,
                "High": 2325.0,
                "Low": 2306.75
            },
            {
                "upd_Date": "2023-02-07T00:00:00",
                "price": 2305.9,
                "volume": 225094.0,
                "Open": 2312.0,
                "High": 2327.0,
                "Low": 2293.1
            },
            {
                "upd_Date": "2023-02-08T00:00:00",
                "price": 2351.8,
                "volume": 268850.0,
                "Open": 2311.3,
                "High": 2359.6,
                "Low": 2307.15
            },
            {
                "upd_Date": "2023-02-09T00:00:00",
                "price": 2355.4,
                "volume": 88852.0,
                "Open": 2357.35,
                "High": 2370.8,
                "Low": 2334.05
            },
            {
                "upd_Date": "2023-02-10T00:00:00",
                "price": 2336.35,
                "volume": 127318.0,
                "Open": 2357.0,
                "High": 2357.0,
                "Low": 2321.6
            },
            {
                "upd_Date": "2023-02-13T00:00:00",
                "price": 2322.75,
                "volume": 79836.0,
                "Open": 2337.6,
                "High": 2350.0,
                "Low": 2314.05
            },
            {
                "upd_Date": "2023-02-14T00:00:00",
                "price": 2377.4,
                "volume": 124813.0,
                "Open": 2325.2,
                "High": 2381.0,
                "Low": 2324.0
            },
            {
                "upd_Date": "2023-02-15T00:00:00",
                "price": 2430.1,
                "volume": 367072.0,
                "Open": 2387.8,
                "High": 2437.0,
                "Low": 2375.0
            },
            {
                "upd_Date": "2023-02-16T00:00:00",
                "price": 2429.65,
                "volume": 277368.0,
                "Open": 2450.0,
                "High": 2463.0,
                "Low": 2425.0
            },
            {
                "upd_Date": "2023-02-17T00:00:00",
                "price": 2439.85,
                "volume": 105936.0,
                "Open": 2424.95,
                "High": 2446.9,
                "Low": 2410.6
            }
        ]

        var datePrice = [];

        datePrice = apiData.map(function (item) {
            return [new Date(item.upd_Date), item.price];
        });

        var seriesData = [{
            name : "Price",
            data : datePrice
        }]

        var options = {
            chart: {
                id: 'area-datetime',
                type: 'area',
                height: 350,
                zoom: {
                    autoScaleYaxis: true,
                    type: 'xy',
                },
                events: {
                    updated: function(chartContext, config) {
                    }
                },
                toolbar: {
                    show: false,
                    tools: {
                        download: true,
                        selection: false,
                        zoom: false,
                        zoomin: false,
                        zoomout: false,
                        pan: false,
                        reset: false | '<img src="/static/icons/reset.png" width="20">',
                        customIcons: []
                    },
                    autoSelected: 'zoom'
                },
            },
            annotations: {
            },
            dataLabels: {
                enabled: false
            },
            markers: {
                size: 0,
                style: 'hollow',
            },
            xaxis: {
                type: 'datetime',
                tickAmount: 4,
                labels: {
                    datetimeUTC: false,
                }
            },
            yaxis: {
                opposite: true,
            },
            tooltip: {
                shared: true,
                x: {
                    format: 'dd MMM yyyy h:mm tt'
                }
            },
            fill: {
                type: 'gradient',
                gradient: {
                    shadeIntensity: 1,
                    opacityFrom: 0.7,
                    opacityTo: 0.9,
                    stops: [0, 95, 100]
                }
            },
            legend : {
                position: 'top',
                horizontalAlign: 'left',
                // offsetX: 0,
                // offsetY: 10
            },
            series: seriesData,
            stroke: {
                width: 2,
                curve: 'straight',
                colors: ['#21B7A8']
            },
            colors: ['#21B7A8'],
            grid: {
                borderColor: '#F3F3F7',
            }
        }
        var chart = new ApexCharts(document.querySelector("#index-chart"), options);

        chart.render();
    </script>
</head>
<body>
    <div id="index-chart"></div>
</body>
</html>
