/* ---------- functions used to demonsatration ---------- */

function randNum() {
    return ((Math.floor(Math.random() * (1 + 40 - 20))) + 20) * 1200;
}

function randNum2() {
    return ((Math.floor(Math.random() * (1 + 40 - 20))) + 20) * 500;
}

function randNum3() {
    return ((Math.floor(Math.random() * (1 + 40 - 20))) + 20) * 300;
}

function randNum4() {
    return ((Math.floor(Math.random() * (1 + 40 - 20))) + 20) * 100;
}

function randNumFB() {
    return ((Math.floor(Math.random() * (1 + 40 - 20))) + 20);
}

function randNumTW() {
    return ((Math.floor(Math.random() * (1 + 40 - 20))) + 20);
}

function unir(x, y) {
    var c = new Array();
    for (var i in x) {
        c.push([x[i], y[i]]);
    }
    return c;
}

function graficar(periodo, demanda, pronostico) {

    /* ---------- Main Chart ---------- */
        var pageviews = [[1, 5 + randNum()], [2, 10 + randNum()], [3, 15 + randNum()], [4, 20 + randNum()], [5, 25 + randNum()], [6, 30 + randNum()], [7, 35 + randNum()], [8, 40 + randNum()], [9, 45 + randNum()], [10, 50 + randNum()], [11, 55 + randNum()], [12, 60 + randNum()], [13, 65 + randNum()], [14, 70 + randNum()], [15, 75 + randNum()], [16, 80 + randNum()], [17, 85 + randNum()], [18, 90 + randNum()], [19, 85 + randNum()], [20, 80 + randNum()], [21, 75 + randNum()], [22, 80 + randNum()], [23, 75 + randNum()], [24, 70 + randNum()], [25, 65 + randNum()], [26, 75 + randNum()], [27, 80 + randNum()], [28, 85 + randNum()], [29, 90 + randNum()], [30, 95 + randNum()]];
        var visits = [[1, randNum2() - 10], [2, randNum2() - 10], [3, randNum2() - 10], [4, randNum2()], [5, randNum2()], [6, 4 + randNum2()], [7, 5 + randNum2()], [8, 6 + randNum2()], [9, 6 + randNum2()], [10, 8 + randNum2()], [11, 9 + randNum2()], [12, 10 + randNum2()], [13, 11 + randNum2()], [14, 12 + randNum2()], [15, 13 + randNum2()], [16, 14 + randNum2()], [17, 15 + randNum2()], [18, 15 + randNum2()], [19, 16 + randNum2()], [20, 17 + randNum2()], [21, 18 + randNum2()], [22, 19 + randNum2()], [23, 20 + randNum2()], [24, 21 + randNum2()], [25, 14 + randNum2()], [26, 24 + randNum2()], [27, 25 + randNum2()], [28, 26 + randNum2()], [29, 27 + randNum2()], [30, 31 + randNum2()]];
        var perdem = unir(periodo, demanda);
        var perpro = unir(periodo, pronostico);
        var plot = $.plot($("#stats-chart2"),
                [
                    {data: perdem, label: "Demanda(y)"},
                    {data: perpro, label: "Pronóstico(ft)"}], {
            series: {
                lines: {show: true,
                    lineWidth: 2
                },
                points: {show: true,
                    lineWidth: 2
                },
                shadowSize: 0
            },
            grid: {hoverable: true,
                clickable: true,
                tickColor: "#f9f9f9",
                borderWidth: 0
            },
            legend: {
                show: false
            },
            colors: ["#2FABE9", "#FA5833"],
            xaxis: {ticks: 15, tickDecimals: 0},
            yaxis: {ticks: 5, tickDecimals: 0},
        });

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                position: 'absolute',
                display: 'none',
                top: y + 5,
                left: x + 5,
                border: '1px solid #fdd',
                padding: '2px',
                'background-color': '#dfeffc',
                opacity: 0.80
            }).appendTo("body").fadeIn(200);
        }

        var previousPoint = null;
        $("#stats-chart2").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));

            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);

                    showTooltip(item.pageX, item.pageY,
                            item.series.label + " of " + x + " = " + y);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });

    

    /* ---------- Facebook Chart ---------- */
    if ($("#facebookChart").length) {
        var likes = [[1, 5 + randNumFB()], [2, 10 + randNumFB()], [3, 15 + randNumFB()], [4, 20 + randNumFB()], [5, 25 + randNumFB()], [6, 30 + randNumFB()], [7, 35 + randNumFB()], [8, 40 + randNumFB()], [9, 45 + randNumFB()], [10, 50 + randNumFB()], [11, 55 + randNumFB()], [12, 60 + randNumFB()], [13, 65 + randNumFB()], [14, 70 + randNumFB()], [15, 75 + randNumFB()], [16, 80 + randNumFB()], [17, 85 + randNumFB()], [18, 90 + randNumFB()], [19, 85 + randNumFB()], [20, 80 + randNumFB()], [21, 75 + randNumFB()], [22, 80 + randNumFB()], [23, 75 + randNumFB()], [24, 70 + randNumFB()], [25, 65 + randNumFB()], [26, 75 + randNumFB()], [27, 80 + randNumFB()], [28, 85 + randNumFB()], [29, 90 + randNumFB()], [30, 95 + randNumFB()]];

        var plot = $.plot($("#facebookChart"),
                [{data: likes, label: "Fans"}], {
            series: {
                lines: {show: true,
                    lineWidth: 2,
                    fill: true, fillColor: {colors: [{opacity: 0.5}, {opacity: 0.2}]}
                },
                points: {show: true,
                    lineWidth: 2
                },
                shadowSize: 0
            },
            grid: {hoverable: true,
                clickable: true,
                tickColor: "#f9f9f9",
                borderWidth: 0
            },
            colors: ["#3B5998"],
            xaxis: {ticks: 6, tickDecimals: 0},
            yaxis: {ticks: 3, tickDecimals: 0},
        });

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                position: 'absolute',
                display: 'none',
                top: y + 5,
                left: x + 5,
                border: '1px solid #fdd',
                padding: '2px',
                'background-color': '#dfeffc',
                opacity: 0.80
            }).appendTo("body").fadeIn(200);
        }

        var previousPoint = null;
        $("#facebookChart").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));

            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);

                    showTooltip(item.pageX, item.pageY,
                            item.series.label + " of " + x + " = " + y);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });

    }

    /* ---------- Twitter Chart ---------- */
    if ($("#twitterChart").length) {
        var followers = [[1, 5 + randNumTW()], [2, 10 + randNumTW()], [3, 15 + randNumTW()], [4, 20 + randNumTW()], [5, 25 + randNumTW()], [6, 30 + randNumTW()], [7, 35 + randNumTW()], [8, 40 + randNumTW()], [9, 45 + randNumTW()], [10, 50 + randNumTW()], [11, 55 + randNumTW()], [12, 60 + randNumTW()], [13, 65 + randNumTW()], [14, 70 + randNumTW()], [15, 75 + randNumTW()], [16, 80 + randNumTW()], [17, 85 + randNumTW()], [18, 90 + randNumTW()], [19, 85 + randNumTW()], [20, 80 + randNumTW()], [21, 75 + randNumTW()], [22, 80 + randNumTW()], [23, 75 + randNumTW()], [24, 70 + randNumTW()], [25, 65 + randNumTW()], [26, 75 + randNumTW()], [27, 80 + randNumTW()], [28, 85 + randNumTW()], [29, 90 + randNumTW()], [30, 95 + randNumTW()]];

        var plot = $.plot($("#twitterChart"),
                [{data: followers, label: "Followers"}], {
            series: {
                lines: {show: true,
                    lineWidth: 2,
                    fill: true, fillColor: {colors: [{opacity: 0.5}, {opacity: 0.2}]}
                },
                points: {show: true,
                    lineWidth: 2
                },
                shadowSize: 0
            },
            grid: {hoverable: true,
                clickable: true,
                tickColor: "#f9f9f9",
                borderWidth: 0
            },
            colors: ["#1BB2E9"],
            xaxis: {ticks: 6, tickDecimals: 0},
            yaxis: {ticks: 3, tickDecimals: 0},
        });

        function showTooltip(x, y, contents) {
            $('<div id="tooltip">' + contents + '</div>').css({
                position: 'absolute',
                display: 'none',
                top: y + 5,
                left: x + 5,
                border: '1px solid #fdd',
                padding: '2px',
                'background-color': '#dfeffc',
                opacity: 0.80
            }).appendTo("body").fadeIn(200);
        }

        var previousPoint = null;
        $("#twitterChart").bind("plothover", function (event, pos, item) {
            $("#x").text(pos.x.toFixed(2));
            $("#y").text(pos.y.toFixed(2));

            if (item) {
                if (previousPoint != item.dataIndex) {
                    previousPoint = item.dataIndex;

                    $("#tooltip").remove();
                    var x = item.datapoint[0].toFixed(2),
                            y = item.datapoint[1].toFixed(2);

                    showTooltip(item.pageX, item.pageY,
                            item.series.label + " of " + x + " = " + y);
                }
            } else {
                $("#tooltip").remove();
                previousPoint = null;
            }
        });

    }

    /* ---------- Demographics Bar Chart ---------- */
    if ($('.verticalChart')) {

        $('.singleBar').each(function () {

            var percent = $(this).find('.value span').html();

            $(this).find('.value').animate({height: percent}, 2000, function () {

                $(this).find('span').fadeIn();

            });

        });

    }

    /* ---------- Sparkline Charts ---------- */
    //generate random number for charts
    randNum = function () {
        //return Math.floor(Math.random()*101);
        return (Math.floor(Math.random() * (1 + 40 - 20))) + 20;
    }

    var chartColours = ['#2FABE9', '#FA5833', '#b9e672', '#bbdce3', '#9a3b1b', '#5a8022', '#2c7282'];

    //sparklines (making loop with random data for all 7 sparkline)
    i = 1;
    for (i = 1; i < 9; i++) {
        var data = [[1, 3 + randNum()], [2, 5 + randNum()], [3, 8 + randNum()], [4, 11 + randNum()], [5, 14 + randNum()], [6, 17 + randNum()], [7, 20 + randNum()], [8, 15 + randNum()], [9, 18 + randNum()], [10, 22 + randNum()]];
        placeholder = '.sparkLineStats' + i;

        if (retina()) {

            $(placeholder).sparkline(data, {
                width: 200, //Width of the chart - Defaults to 'auto' - May be any valid css width - 1.5em, 20px, etc (using a number without a unit specifier won't do what you want) - This option does nothing for bar and tristate chars (see barWidth)
                height: 60, //Height of the chart - Defaults to 'auto' (line height of the containing tag)
                lineColor: '#2FABE9', //Used by line and discrete charts to specify the colour of the line drawn as a CSS values string
                fillColor: '#f2f7f9', //Specify the colour used to fill the area under the graph as a CSS value. Set to false to disable fill
                spotColor: '#467e8c', //The CSS colour of the final value marker. Set to false or an empty string to hide it
                maxSpotColor: '#b9e672', //The CSS colour of the marker displayed for the maximum value. Set to false or an empty string to hide it
                minSpotColor: '#FA5833', //The CSS colour of the marker displayed for the mimum value. Set to false or an empty string to hide it
                spotRadius: 2, //Radius of all spot markers, In pixels (default: 1.5) - Integer
                lineWidth: 1//In pixels (default: 1) - Integer
            });

            //only firefox sux in this case
            if (jQuery.browser.mozilla) {

                if (!!navigator.userAgent.match(/Trident\/7\./)) {

                    $(placeholder).css('zoom', 0.5);

                } else {

                    $(placeholder).css('MozTransform', 'scale(0.5,0.5)').css('margin-top', '-10px');
                    $(placeholder).css('height', '30px').css('width', '100px').css('margin-left', '-20px').css('margin-right', '40px');

                }

            } else {
                $(placeholder).css('zoom', 0.5);
            }

        } else {

            $(placeholder).sparkline(data, {
                width: 100, //Width of the chart - Defaults to 'auto' - May be any valid css width - 1.5em, 20px, etc (using a number without a unit specifier won't do what you want) - This option does nothing for bar and tristate chars (see barWidth)
                height: 30, //Height of the chart - Defaults to 'auto' (line height of the containing tag)
                lineColor: '#2FABE9', //Used by line and discrete charts to specify the colour of the line drawn as a CSS values string
                fillColor: '#f2f7f9', //Specify the colour used to fill the area under the graph as a CSS value. Set to false to disable fill
                spotColor: '#467e8c', //The CSS colour of the final value marker. Set to false or an empty string to hide it
                maxSpotColor: '#b9e672', //The CSS colour of the marker displayed for the maximum value. Set to false or an empty string to hide it
                minSpotColor: '#FA5833', //The CSS colour of the marker displayed for the mimum value. Set to false or an empty string to hide it
                spotRadius: 2, //Radius of all spot markers, In pixels (default: 1.5) - Integer
                lineWidth: 1//In pixels (default: 1) - Integer
            });

        }

    }


}