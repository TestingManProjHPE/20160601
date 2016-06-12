/**
 * Created by yxin on 4/27/2016.
 */
var DimpleInContent = function() {
    "use strict";

    //For the Ring
    var ringDimpleContent = function() {
        var svg = dimple.newSvg("#dimpleRingContent", 590, 400);
        url = 'http://localhost:8080/jf/test/requirements/reqcover';
        d3.json(url,function(jsonstr) {
            data = jsonstr.data;
            var myChart = new dimple.chart(svg, data);
            myChart.setBounds(20, 20, 460, 360);
            myChart.addMeasureAxis("p", "数量");
            var ring = myChart.addSeries("类型", dimple.plot.pie);
            ring.innerRadius = "50%";
            myChart.addLegend(500, 20, 90, 300, "left");
            myChart.draw();
        })
    };

    //For the Ring
    var ringDimpleContenttwo = function() {
        var svg = dimple.newSvg("#dimpleRingContenttwo", 590, 400);
        url = 'http://localhost:8080/jf/test/requirements/reqpass';
        d3.json(url,function(jsonstr) {
            data = jsonstr.data;
            var myChart = new dimple.chart(svg, data);
            myChart.setBounds(20, 20, 460, 360);
            myChart.addMeasureAxis("p", "数量");
            var ring = myChart.addSeries("类型", dimple.plot.pie);
            ring.innerRadius = "50%";
            myChart.addLegend(500, 20, 90, 300, "left");
            myChart.draw();
        })
    };

    return {
        //main function to initiate template pages
        init: function() {
            ringDimpleContent();
            ringDimpleContenttwo();
        }
    };

}();
