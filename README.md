# javamancer
Java "Port" of Neuromancer PC Game (1989) with Game Assets Browser

<p align="center">
  <img src="/images/javamancer-main.png" height="240">
  <a href="neuro-game-box.md"><img src="/images/neuromancer-pc-box-2.jpeg" height="240"></a>
</p>

Inspired by and partially based upon the [Reuromancer Windows64](https://github.com/HenadziMatuts/Reuromancer) port by [Henadzi Matuts]( https://github.com/HenadziMatuts) and adapted to Java/JavaFX.

## Requirements
- This engine will require the original NEURO1.DAT and NEURO2.DAT resource files to operate.  You can obtain a copy via by searching the internet.
- For now, under development and requires NetBeans IDE for running (unless you have Maven skills).

## What is Neuromancer PC?
Neuromancer PC was "1989 Game of the Year" for PCs and shipped in a [22cm x 22cm x 2cm
thick box](neuro-game-box.md).  The box contained a simple manual, two game floppies and a "decoder" wheel.
The decoder wheel was used as DRM(copy protection).  As the floppies could be easily copied,
the designers of the game created an interactive wheel to use during the "on-line" portion
of the game. The user would be presented with a <img src="/images/neuromancer-pc-box-25.jpeg" align="right" height="180"/> three word phrase and would need to type
a reply of a number code to proceed in the game.  For example, you might be presented with the
phrase "Chatsubo" "Ratz" "Chiba City".  The player would rotate the wheel layers such that the
first two phrases (around the outer edge) line up. Then the user would read the number
that appears inside the box with the label for the third phrase word(s). Type in the
number and game continues.
<br clear="left"/>

There are a few [web hosted "code wheels"](http://www.mlsite.net/neuro/) as the lookup table for the wheel has been
well documented for a while.  I even wrote a simple [Neuromancer code lookup in JavaFX](https://github.com/maehem/neuromancer-pax-wheel) and
might optionally incorporate it into the game at some point.

While I could recap what the phenomena Neuromancer PC is, there are numerous sites and
YouTube videos with descriptions, walk-throughs and commentary. Here's a list of a few
that I find interesting: 
 - [SmallInteger - Text Walkthrough](https://www.smallinteger.com/random/neuromancer.txt)
 - [Walkthrough King - Text Walkthrough](https://www.walkthroughking.com/text/neuromancer.aspx)
 - [Adventurer's Guild - Walkthrough with screenshots](https://advgamer.blogspot.com/2013/05/game-32-neuromancer-learning-ropes.html)

## What makes this interesting for you?
In a nutshell, I was hooked when I first read the Neuromancer book. I had just graduated
college, I was already a CompuServe dial-up service junkie and even spent time on a few
dial-up BBSs.  I had seen Neuromancer released on C64 sometime in early 1989 or even 1988,
but I didn't own a Commodore 64 or even a desktop PC at the time. However, by 1990, I was
on my second job out of college at none other than [Sun Microsystems](https://en.wikipedia.org/wiki/Sun_Microsystems), 
were leaders in the design and manufacture of 
early internet hardware.  Sun was already directly on the new
thing called the Internet and had state-of-the-art hardware called "workstations" that put PCs to shame. As employees,
we had email (still a new thing) <img src="/images/neuromancer-book.jpeg" align="right" height="240"/> and access to a thing called Usenet, kind of a text version of
web pages, where vast information was searchable and groups of like-minded people could group and
share ideas and information about subjects they were interested in.  Similar to BBSs of the previous
decade except no dial-up (always connected) and the places you could explore expanded every day.
The Neuromancer game was somewhat modeled after this.  The player would find a public
terminal and simply connect to get email, BBS or banking.  When the player finally had some
hardware, they could find instead, a "Jack" and gain access to a graphical version of
cyberspace, where they could navigate, forward-back, left-right to sites and access
more important types of information.  Instead of passwords, cyberspace sites had "ICE"
that the user would battle with in a turn based manner until they died or the site ICE
was smashed through.  Once through, the player could access the site via a BBS style terminal
interface and download more powerful tools to battle the next ICE with. They might also
peruse the site to gain hints and information about where to go next in the game.
<br clear="left"/>
I thought this made a wonderful metaphor for how I'd already been accessing the
real internet already. In my mind the internet felt like some kind of ocean to be
explored, I spend many on work and off work hours using the internet at my terminal there. 
At the time, the Matrix presented in the game, as well as
the book, felt like a plausible approach for an actual future internet.  
Virtual Reality (VR) was emerging as an idea back then as well and even got involved
as an engineer in it while working at Sun.  VR's promise was something like "headphones
for your eyes".  But I think for it's time, it was a harder problem than any of us
thought. Here we are 35 years later and Meta and others still have work to do.  All in all, I
was sucked completely in by cyberpunk and with a strong bias towards the techicnal how-it-worked
and how it could work. For all the corny and trope-ish things found in Neuromancer PC game,
I feel that they got a lot right in regards to how I viewed the preset and the possible
future back in 1989...  and still do.

## What does it do?
Currently, this software is able to uncompress and decode the image and text game
assets from Neuromancer PC .DAT files, you will need a copy of the original floppy files, I
won't distribute them here.  Many abandon-ware sites have archived them. Once running,
the application will scan your NEURO1.DAT and NEURO2.DAT files, extract and cache
the assets to your user computer.  You can then use the browser function to explore
the sprites, backgrounds, animations and dialog text.  In the future, I will write
an engine to play the game with the cached assets.

# Is this an emulator for the game?
No.  Any engine that appears here (it's not working yet) is a new rewrite, in JavaFX.
I'm hoping that won't be too much of a can-of-worms as I now have access to the pic
assets and text assets as well as plenty of walk through resources. Small matter of
code. Famous last words.

# Where did you get the idea for this?
Around 2020 (perhaps during covid lock-down) I had an idea to extract the data from
the original files and maybe do something with them.  I really liked playing the game
in a DOS emulator, but wished there was a way to play it directly on something more
modern and maybe even be able to add things to or change things (like imagery) in the game.  I initially
probed around the DAT files with a hex editor but my knowledge of old game encoding
and compression were lacking.  I really couldn't make sense of it so I gave up.  A few
months later, I had the idea of coding something similar from scratch.  I wanted to make
a Neuromancer-like game with my own characters but set in a very similar world (hacking,
BBS, Matrix ICE busting, etc.). So I started some experiments with a "room" where a
character could walk around, talk to a NPC, had a simple inventory. I worked out these
constraints over a few months and ended up with something rough but pleasing.  I started
to add rooms to the experiment and soon had a multi room area that the player could
walk around and affect the environment.  I also started making notes for my own character
and setting.  Like in Neuromancer PC, my character starts with nothing and the NPCs
drop hints about what to do next.  My setting is also in a cyberpunk future but set in
the American South West (go with what you know).  Different challenges than Chiba City.

During this process, I'd realized that I had three projects to juggle.  A Neuromancer PC
clone "[Chiba City Blues](https://github.com/maehem/ChibaCityBlues)", my game story idea
"Jack" and an engine "Abyss".  So I divided the work up such that both "Neuro-clone"
and "Jack" ran on the same game engine, called "[Abyss](https://github.com/maehem/Abyss)".
 I've been working on those three projects for about three years now, no rush.  
Javamancer is not any of those things, so now I have four related projects.

### Abyss engine
A little rough around the edges, but a running game on the 
"[Abyss](https://github.com/maehem/Abyss)" engine. Left: My version of Chatsubo Bar.  
Right: My take on the Cyberspace interface.  Still working with an artist for character
designs and such. This is all prototype stuff so don't judge.
<p>
<img src="/images/abyss-engine-screenhot-realworld-1.png" height="240"/>
<img src="/images/abyss-engine-screenshot-matrix-1.png" height="240"/>
</p>

## Fast forward to now (2024)...

In researcing more for "Chiba City Blues" I ran across the work of [Henadzi Matuts](https://habr.com/en/articles/352050/) .
Hendazi had the same initial curiosity but did exactly what I couldn't, he used his
skills in 8088/8086 assembler to reverse engineer the Neuromancer PC game byte by byte.
He went further and wrote a series of four amazing blog posts that break down the content
encoding on the original Neuromancer PC game DAT files.  He had done it back in 2018,
but somehow I had not stumbled across his work until 2024.  However, he decided to
port Neuromancer PC to Windows (64-bit) in C++.  But his detailed study of the game's
data was eye opening for me.  As a bonus, he had released his work as MIT license.
Since I planned to rewrite parts of what he did in a different language (Java) it made
attribution much easier. Hendazi is credited at the top of each of my source files,
also MIT license.  I've included Handazi's code here in my source tree for reference.
I've made no changes (except for an additional note or comment here and there). But
if you want to study Hendazi's code, I suggest you go straight to their source at:
[Hendazi Matut's Reuromancer](https://github.com/HenadziMatuts/Reuromancer)
Handazi's Blogs are in Russian, so I suggest you use Chrome to view them and let
Google translate it for you.

From there, I was able to unpack and decode all the assets, in Java, in a couple
weeks.  So here is "Javamancer" in it's current rough state.  I'm sure there are
issues and it comes with no instructions.  You'll probably need the latest Netbeans
and Java to run it.

![Screenshot of Javamancer Resource Browser showing a game asset background of PIC format.](/images/javamancer-browser-pic.png)

# Other Mentions
In this process, I ran across a wonderful cross-platform tool called [imHex](https://github.com/WerWolv/ImHex) . It was
invaluable in probing the decoded data from the DAT files as well as debugging
my custom PNG encoder.

## License
MIT License
