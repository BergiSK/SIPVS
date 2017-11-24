<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sipvs="http://www.example.org/sipvs" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <style>
                    .picker__holder,.picker__frame{bottom:0;left:0;right:0;top:100%}.picker__holder{position:fixed;-webkit-transition:background 0.15s ease-out,top 0s 0.15s;-moz-transition:background 0.15s ease-out,top 0s 0.15s;transition:background 0.15s ease-out,top 0s 0.15s;-webkit-backface-visibility:hidden}.picker__frame{position:absolute;margin:0 auto;min-width:256px;width:300px;max-height:350px;-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=0)";filter:alpha(opacity=0);-moz-opacity:0;opacity:0;-webkit-transition:all 0.15s ease-out;-moz-transition:all 0.15s ease-out;transition:all 0.15s ease-out}@media (min-height: 28.875em){.picker__frame{overflow:visible;top:auto;bottom:-100%;max-height:80%}}@media (min-height: 40.125em){.picker__frame{margin-bottom:7.5%}}.picker__wrap{display:table;width:100%;height:100%}@media (min-height: 28.875em){.picker__wrap{display:block}}.picker__box{background:#ffffff;display:table-cell;vertical-align:middle}@media (min-height: 28.875em){.picker__box{display:block;border:1px solid #777777;border-top-color:#898989;border-bottom-width:0;-webkit-border-radius:5px 5px 0 0;-moz-border-radius:5px 5px 0 0;border-radius:5px 5px 0 0;-webkit-box-shadow:0 12px 36px 16px rgba(0,0,0,0.24);-moz-box-shadow:0 12px 36px 16px rgba(0,0,0,0.24);box-shadow:0 12px 36px 16px rgba(0,0,0,0.24)}}.picker--opened .picker__holder{top:0;background:transparent;-ms-filter:"progid:DXImageTransform.Microsoft.gradient(startColorstr=#1E000000,endColorstr=#1E000000)";zoom:1;background:rgba(0,0,0,0.32);-webkit-transition:background 0.15s ease-out;-moz-transition:background 0.15s ease-out;transition:background 0.15s ease-out}.picker--opened .picker__frame{top:0;-ms-filter:"progid:DXImageTransform.Microsoft.Alpha(Opacity=100)";filter:alpha(opacity=100);-moz-opacity:1;opacity:1}@media (min-height: 35.875em){.picker--opened .picker__frame{top:10%;bottom:20% auto}}.picker__input.picker__input--active{border-color:#E3F2FD}.picker__frame{margin:0 auto;max-width:325px}@media (min-height: 38.875em){.picker--opened .picker__frame{top:10%;bottom:auto}}.picker__box{padding:0 1em}.picker__header{text-align:center;position:relative;margin-top:.75em}.picker__month,.picker__year{display:inline-block;margin-left:.25em;margin-right:.25em}.picker__select--month,.picker__select--year{height:2em;padding:0;margin-left:.25em;margin-right:.25em}.picker__select--month.browser-default{display:inline;background-color:#FFFFFF;width:40%}.picker__select--year.browser-default{display:inline;background-color:#FFFFFF;width:25%}.picker__select--month:focus,.picker__select--year:focus{border-color:rgba(0,0,0,0.05)}.picker__nav--prev,.picker__nav--next{position:absolute;padding:.5em 1.25em;width:1em;height:1em;box-sizing:content-box;top:-0.25em}.picker__nav--prev{left:-1em;padding-right:1.25em}.picker__nav--next{right:-1em;padding-left:1.25em}.picker__nav--disabled,.picker__nav--disabled:hover,.picker__nav--disabled:before,.picker__nav--disabled:before:hover{cursor:default;background:none;border-right-color:#f5f5f5;border-left-color:#f5f5f5}.picker__table{text-align:center;border-collapse:collapse;border-spacing:0;table-layout:fixed;font-size:1rem;width:100%;margin-top:.75em;margin-bottom:.5em}.picker__table th,.picker__table td{text-align:center}.picker__table td{margin:0;padding:0}.picker__weekday{width:14.285714286%;font-size:.75em;padding-bottom:.25em;color:#999999;font-weight:500}@media (min-height: 33.875em){.picker__weekday{padding-bottom:.5em}}.picker__day--today{position:relative;color:#595959;letter-spacing:-.3;padding:.75rem 0;font-weight:400;border:1px solid transparent}.picker__day--disabled:before{border-top-color:#aaaaaa}.picker__day--infocus:hover{cursor:pointer;color:#000;font-weight:500}.picker__day--outfocus{display:none;padding:.75rem 0;color:#fff}.picker__day--outfocus:hover{cursor:pointer;color:#dddddd;font-weight:500}.picker__day--highlighted:hover,.picker--focused .picker__day--highlighted{cursor:pointer}.picker__day--selected,.picker__day--selected:hover,.picker--focused .picker__day--selected{border-radius:50%;-webkit-transform:scale(.75);-moz-transform:scale(.75);-ms-transform:scale(.75);-o-transform:scale(.75);transform:scale(.75);background:#0089ec;color:#ffffff}.picker__day--disabled,.picker__day--disabled:hover,.picker--focused .picker__day--disabled{background:#f5f5f5;border-color:#f5f5f5;color:#dddddd;cursor:default}.picker__day--highlighted.picker__day--disabled,.picker__day--highlighted.picker__day--disabled:hover{background:#bbbbbb}.picker__footer{text-align:center;display:flex;align-items:center;justify-content:space-between}.picker__button--today,.picker__button--clear,.picker__button--close{border:1px solid #ffffff;background:#ffffff;font-size:.8em;padding:.66em 0;font-weight:bold;width:33%;display:inline-block;vertical-align:bottom}.picker__button--today:hover,.picker__button--clear:hover,.picker__button--close:hover{cursor:pointer;color:#000000;background:#b1dcfb;border-bottom-color:#b1dcfb}.picker__button--today:focus,.picker__button--clear:focus,.picker__button--close:focus{background:#b1dcfb;border-color:rgba(0,0,0,0.05);outline:none}.picker__button--today:before,.picker__button--clear:before,.picker__button--close:before{position:relative;display:inline-block;height:0}.picker__button--today:before,.picker__button--clear:before{content:" ";margin-right:.45em}.picker__button--today:before{top:-0.05em;width:0;border-top:0.66em solid #0059bc;border-left:.66em solid transparent}.picker__button--clear:before{top:-0.25em;width:.66em;border-top:3px solid #ee2200}.picker__button--close:before{content:"\D7";top:-0.1em;vertical-align:top;font-size:1.1em;margin-right:.35em;color:#777777}.picker__button--today[disabled],.picker__button--today[disabled]:hover{background:#f5f5f5;border-color:#f5f5f5;color:#dddddd;cursor:default}.picker__button--today[disabled]:before{border-top-color:#aaaaaa}.picker__box{border-radius:2px;overflow:hidden}.picker__date-display{text-align:center;background-color:#26a69a;color:#fff;padding-bottom:15px;font-weight:300}.picker__nav--prev:hover,.picker__nav--next:hover{cursor:pointer;color:#000000;background:#a1ded8}.picker__weekday-display{background-color:#1f897f;padding:10px;font-weight:200;letter-spacing:.5;font-size:1rem;margin-bottom:15px}.picker__month-display{text-transform:uppercase;font-size:2rem}.picker__day-display{font-size:4.5rem;font-weight:400}.picker__year-display{font-size:1.8rem;color:rgba(255,255,255,0.4)}.picker__box{padding:0}.picker__calendar-container{padding:0 1rem}.picker__calendar-container thead{border:none}.picker__table{margin-top:0;margin-bottom:.5em}.picker__day--infocus{color:#595959;letter-spacing:-.3;padding:.75rem 0;font-weight:400;border:1px solid transparent}.picker__day.picker__day--today{color:#26a69a}.picker__day.picker__day--today.picker__day--selected{color:#fff}.picker__weekday{font-size:.9rem}.picker__day--selected,.picker__day--selected:hover,.picker--focused .picker__day--selected{border-radius:50%;-webkit-transform:scale(.9);-moz-transform:scale(.9);-ms-transform:scale(.9);-o-transform:scale(.9);transform:scale(.9);background-color:#26a69a;color:#ffffff}.picker__day--selected.picker__day--outfocus,.picker__day--selected:hover.picker__day--outfocus,.picker--focused .picker__day--selected.picker__day--outfocus{background-color:#a1ded8}.picker__footer{text-align:right;padding:5px 10px}.picker__close,.picker__today{font-size:1.1rem;padding:0 1rem;color:#26a69a}.picker__nav--prev:before,.picker__nav--next:before{content:" ";border-top:.5em solid transparent;border-bottom:.5em solid transparent;border-right:0.75em solid #676767;width:0;height:0;display:block;margin:0 auto}.picker__nav--next:before{border-right:0;border-left:0.75em solid #676767}button.picker__today:focus,button.picker__clear:focus,button.picker__close:focus{background-color:#a1ded8}.picker__list{list-style:none;padding:0.75em 0 4.2em;margin:0}.picker__list-item{border-bottom:1px solid #dddddd;border-top:1px solid #dddddd;margin-bottom:-1px;position:relative;background:#ffffff;padding:.75em 1.25em}@media (min-height: 46.75em){.picker__list-item{padding:.5em 1em}}.picker__list-item:hover{cursor:pointer;color:#000000;background:#b1dcfb;border-color:#0089ec;z-index:10}.picker__list-item--highlighted{border-color:#0089ec;z-index:10}.picker__list-item--highlighted:hover,.picker--focused .picker__list-item--highlighted{cursor:pointer;color:#000000;background:#b1dcfb}.picker__list-item--selected,.picker__list-item--selected:hover,.picker--focused .picker__list-item--selected{background:#0089ec;color:#ffffff;z-index:10}.picker__list-item--disabled,.picker__list-item--disabled:hover,.picker--focused .picker__list-item--disabled{background:#f5f5f5;border-color:#f5f5f5;color:#dddddd;cursor:default;border-color:#dddddd;z-index:auto}.picker--time .picker__button--clear{display:block;width:80%;margin:1em auto 0;padding:1em 1.25em;background:none;border:0;font-weight:500;font-size:.67em;text-align:center;text-transform:uppercase;color:#666}.picker--time .picker__button--clear:hover,.picker--time .picker__button--clear:focus{color:#000000;background:#b1dcfb;background:#ee2200;border-color:#ee2200;cursor:pointer;color:#ffffff;outline:none}.picker--time .picker__button--clear:before{top:-0.25em;color:#666;font-size:1.25em;font-weight:bold}.picker--time .picker__button--clear:hover:before,.picker--time .picker__button--clear:focus:before{color:#ffffff}.picker--time .picker__frame{min-width:256px;max-width:320px}.picker--time .picker__box{font-size:1em;background:#f2f2f2;padding:0}@media (min-height: 40.125em){.picker--time .picker__box{margin-bottom:5em}}

                    [type="checkbox"]:not(:checked), [type="checkbox"]:checked{
                    left: 30px;
                    top: 34px;
                    visibility: visible;
                    }
                    .left{
                    float: left;
                    }
                    .center{
                    text-align: center;
                    }

                    .body{
                    width: 60%;
                    margin: auto;
                    padding: 20px;
                    margin-bottom: 50px;
                    margin-top: 50px;
                    box-shadow: 0 1px 4px 0 rgba(0,0,0,0.37);
                    }

                    .validation-result{
                    padding: 20px;
                    margin: 20px;
                    }

                    .icon{
                    padding: 10px !important;
                    }

                    .back{
                    margin-bottom: 0px;
                    margin-top: 20px;
                    }

                    .label{
                    font-weight: bold;
                    padding-right: 10px;
                    font-size: 1.2rem;
                    }
                    .value{
                    font-size: 1.2rem
                    }

                    .validationSection i {
                    font-size: 40px;
                    }

                    .validationSection .validBox {
                    color: #43A047 !important;
                    border-color: #43A047 !important;
                    border-style: solid;
                    display: inline-flex;
                    }

                    .validBox .messageBox  {
                    line-height: 40px;
                    }

                    .validationSection .invalidBox {
                    color: #E53935 !important;
                    border-color: #E53935 !important;
                    border-style: solid;
                    display: inline-flex;
                    }

                    .accentBanner {
                    height: 8px;
                    color: #26a69a;
                    }
                    .s6{
                    width:50%;
                    float: left;
                    }
                    h3{
                    font-size: 35px;
                    }
                    h5{
                    font-size: 25px;
                    }
                </style>
            </head>
            <body>
                <div class="row col s12 row body">
                    <h3 class="center" >Details</h3>
                    <h5>Team</h5>

                    <!-- TEAM NAME -->
                    <xsl:apply-templates select="//sipvs:name"/>

                    <!-- TEAM NAME SHORTCUT -->
                    <div class="col s6">
                        <div class="left label">Shortcut:</div>
                        <div class="left value">
                            <xsl:value-of select="//@shortcut" />
                        </div>
                    </div>

                    <!-- ALL PLAYERS -->
                    <xsl:for-each select="//sipvs:player">
                        <xsl:variable name="i" select="position()" />
                        <div class="col s6">
                            <div class="left label">
                                Player
                                <xsl:copy>
                                    <xsl:value-of select="$i"/>
                                </xsl:copy>
                                :
                            </div>
                            <div class="left value">
                                <xsl:value-of select="."/>
                            </div>
                        </div>
                    </xsl:for-each>

                    <!-- VIP room -->
                    <xsl:apply-templates select="//sipvs:vip"/>
                    <div class="row"></div>
                    <hr/>

                    <h5>Contact</h5>


                    <!-- User -->
                    <xsl:apply-templates select="//sipvs:user"/>
                    <div class="row"></div>
                    <div class="row back">
                        <button class="waves-effect waves-light btn" onclick="window.location.href='/form'">Back</button>
                    </div>
                </div>
            </body>
        </html>
    </xsl:template>

    <xsl:template match="sipvs:name">
        <div class="col s6">
            <div class="left label">Team Name:</div>
            <div class="left value">
                <xsl:value-of select="."/>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="sipvs:vip">
        <div class="col s6">
            <div class="left label">VIP room:</div>
            <div class="left value">
                <xsl:choose>
                    <xsl:when test="contains(./text(),'true')">
                        Yes
                    </xsl:when>
                    <xsl:otherwise>
                        No
                    </xsl:otherwise>
                </xsl:choose>
            </div>
        </div>
    </xsl:template>

    <xsl:template match="sipvs:user">
        <div class="col s6">
            <div class="left label">Name:</div>
            <xsl:apply-templates select="//sipvs:firstName"/>
        </div>
        <div class="col s6">
            <div class="left label">Surname:</div>
            <xsl:apply-templates select="//sipvs:lastName"/>
        </div>
        <div class="col s6">
            <div class="left label">Age:</div>
            <xsl:apply-templates select="//sipvs:age"/>
        </div>
        <div class="col s6">
            <div class="left label">e-mail:</div>
            <xsl:apply-templates select="//sipvs:mail"/>
        </div>
    </xsl:template>

    <xsl:template match="sipvs:firstName">
        <div class="left value">
            <xsl:value-of select="."/>
        </div>
    </xsl:template>

    <xsl:template match="sipvs:lastName">
        <div class="left value">
            <xsl:value-of select="."/>
        </div>
    </xsl:template>

    <xsl:template match="sipvs:age">
        <div class="left value">
            <xsl:value-of select="."/>
        </div>
    </xsl:template>

    <xsl:template match="sipvs:mail">
        <div class="left value">
            <xsl:value-of select="."/>
        </div>
    </xsl:template>

</xsl:stylesheet>