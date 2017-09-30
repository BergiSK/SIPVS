<?xml version="1.0" encoding="utf-8"?>
<xsl:stylesheet version="1.0" xmlns:xsl="http://www.w3.org/1999/XSL/Transform" xmlns:sipvs="http://www.example.org/sipvs" xmlns:fo="http://www.w3.org/1999/XSL/Format" >
    <xsl:output method="html"/>

    <xsl:template match="/">
        <html>
            <head>
                <link rel = "stylesheet"
                      href = "https://fonts.googleapis.com/icon?family=Material+Icons"></link>
                <link rel = "stylesheet"
                      href = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/css/materialize.min.css"></link>
                <script type = "text/javascript"
                        src = "https://code.jquery.com/jquery-2.1.1.min.js"></script>
                <script src = "https://cdnjs.cloudflare.com/ajax/libs/materialize/0.97.3/js/materialize.min.js">
                </script>
                <link rel="stylesheet" type="text/css" media="all" href="../styles.css" />
            </head>
            <body>
            <div class="row col s12 row body grey lighten-2">
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
                    <button class="waves-effect waves-light btn" onclick="window.location.href='/form'"><i class="material-icons left">arrow_back</i>Back</button>
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