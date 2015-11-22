c:\IM\convert c:\IM\mapstiles\maps_alpha.png -crop 256x256 -set filename:tile "%%[fx:page.x/256]_%%[fx:page.y/256]" +repage +adjoin C:\IM\mapstiles\tiles\tile-%%[filename:tile].png"
