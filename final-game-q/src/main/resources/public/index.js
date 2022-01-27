let Pacman = {};
let KEY = {};
KEY.LEFT = 37;
KEY.UP = 38;
KEY.RIGHT = 39;
KEY.DOWN = 40;

Pacman.FPS = 30;

// Map element
Pacman.WALL = 0;
Pacman.BISCUIT = 1;
Pacman.EMPTY = 2;
Pacman.GHOST = 3;
Pacman.LARGE_DOT = 4;
Pacman.FRUIT = 5;
Pacman.APPLE = 6;
Pacman.QUICKSILVER = 7;
Pacman.FRIGHTENED_GHOST = 8;

Pacman.GHOST_EYES_ONLY = 9;

// Game state
Pacman.WATING = 10;
Pacman.PLAYING = 11;
Pacman.GAMEOVER = 12;
Pacman.RESET = 13;


Pacman.PACMAN = 20;
Pacman.PACMAN_QUICKSILVER = 21;
let banana;
let apple;
let quicksilver = document.createElement('img')
quicksilver.src = 'https://emojipedia-us.s3.dualstack.us-west-1.amazonaws.com/thumbs/320/google/313/high-voltage_26a1.png'

apple = document.createElement('img');
apple.src = './resource/apple.png'
apple.width = 100
apple.height = 100

banana = document.createElement('img');
banana.src = "https://s3-us-west-2.amazonaws.com/s.cdpn.io/1524180/Banana.png"
banana.width = 100
banana.height = 100

Pacman.MAP = [
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0],
    [0, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 6, 0],
    [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0],
    [0, 4, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 4, 0],
    [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0],
    [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0],
    [0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 1, 0],
    [0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 2, 0, 0, 2, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 2, 3, 3, 3, 3, 2, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 0, 2, 2, 2, 2, 2, 2, 0, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 2, 2, 2, 2, 2, 2, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 2, 2, 2, 2, 20, 2, 2, 2, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 0, 0, 0, 0, 0, 1, 0, 0, 2, 0, 0, 0, 0, 0, 0, 0, 0, 2, 0, 0, 1, 0, 0, 0, 0, 0, 0],
    [0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0],
    [0, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0],
    [0, 4, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 1, 2, 2, 1, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 4, 0],
    [0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0],
    [0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 1, 0, 0, 0],
    [0, 1, 1, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1, 0],
    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0],
    [0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 0],
    [0, 7, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0],
    [0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0]
];

Pacman.Map = function (size) {
    let height = null,
        width = null,
        blockSize = size,
        map = null;

    function reset() {
        map = Pacman.MAP;
        height = map.length;
        width = map[0].length;
    }

    function setMap(_map) {
        map = _map
    }

    function drawBlock(y, x, ctx, direction) {

        let layout = map[y][x];

        if (layout === Pacman.LARGE_DOT) {
            return;
        }

        ctx.beginPath();


        ctx.fillStyle = "#000";
        ctx.fillRect((x * blockSize), (y * blockSize),
            blockSize, blockSize);

        if (layout === Pacman.BISCUIT) {
            ctx.fillStyle = "#FFF";
            ctx.fillRect((x * blockSize) + (blockSize / 2.5),
                (y * blockSize) + (blockSize / 2.5),
                blockSize / 6, blockSize / 6);
        }

        if (layout === Pacman.WALL) {
            ctx.fillStyle = "#0000FF";
            ctx.fillRect((x * blockSize), (y * blockSize),
                blockSize, blockSize);
        }
        if (layout === Pacman.GHOST) {
            // TODO: draw ghost's ghost size too small.
            drawGhost((x * blockSize), y * blockSize, 13, 'green', ctx)
        }
        if (layout === Pacman.FRIGHTENED_GHOST) {
            let colorValue = Math.floor(Date.now() / 30) % 2
            if (colorValue === 0) {
                drawGhost((x * blockSize), y * blockSize, 13, 'blue', ctx)
            } else {
                drawGhost((x * blockSize), y * blockSize, 13, 'white', ctx)
            }

        }
        if (layout === Pacman.FRUIT) {
            drawFruit(x * blockSize, y * blockSize, blockSize + 5, 'banana', ctx)
        }
        if (layout === Pacman.APPLE) {
            drawFruit(x * blockSize, y * blockSize, blockSize, 'apple', ctx)
        }
        if (layout === Pacman.PACMAN) {
            drawPacMan(x * blockSize, y * blockSize, blockSize, 0.25, 1.75, "yellow", ctx, direction)
        }
        if (layout === Pacman.QUICKSILVER) {
            drawFruit(x * blockSize, y * blockSize, blockSize, 'quicksilver', ctx)
        }
        if (layout === Pacman.GHOST_EYES_ONLY) {
            drawGhostEye(x * blockSize, y * blockSize, 13, '', ctx)
        }
        if (layout === Pacman.PACMAN_QUICKSILVER) {
            drawPacMan(x * blockSize, y * blockSize, blockSize, 0.25, 1.75, "red", ctx, direction);
        }

        ctx.closePath();
    }

    function draw(ctx, direction) {
        let i, j, size = blockSize;

        ctx.fillStyle = "#000";
        ctx.fillRect(0, 0, width * size, height * size);

        for (i = 0; i < height; i += 1) {
            for (j = 0; j < width; j += 1) {
                drawBlock(i, j, ctx, direction);
            }
        }
        drawLargeDots(ctx)
    }

    function drawLargeDots(ctx) {
        let dotSize = Date.now() % 900 / 30
        for (let i = 0; i < height; i += 1) {
            for (let j = 0; j < width; j += 1) {
                if (map[i][j] === Pacman.LARGE_DOT) {
                    ctx.beginPath();

                    ctx.fillStyle = "#000";
                    ctx.fillRect((j * blockSize), (i * blockSize),
                        blockSize, blockSize);

                    ctx.fillStyle = "#FFF";
                    ctx.arc((j * blockSize) + blockSize / 2,
                        (i * blockSize) + blockSize / 2,
                        Math.abs(5 - (dotSize / 3)),
                        0,
                        Math.PI * 2, false);
                    ctx.fill();
                    ctx.closePath();
                }
            }
        }
    }

    /**
     * Draw a ghost
     * @param x  The x location coordinate
     * @param y  The y location coordinate
     * @param s size of the ghost
     * @param color color of the ghost
     * @param ctx canvas context
     */
    function drawGhost(x, y, s, color, ctx) {
        const top = y + 2,
            left = x + 2;

        const base = top + s + 3;
        //drawBody
        ctx.fillStyle = color;
        ctx.beginPath();

        ctx.moveTo(left, base);

        ctx.quadraticCurveTo(left, top, left + (s / 2), top);
        ctx.quadraticCurveTo(left + s, top, left + s, base);

        ctx.closePath();
        ctx.fill();

        //draw eyes
        ctx.beginPath();
        ctx.fillStyle = "#FFF";
        ctx.arc(left + 4, top + 6, s / 6, 0, 300, false);
        ctx.arc((left + s) - 4, top + 6, s / 6, 0, 300, false);
        ctx.closePath();
        ctx.fill();

        ctx.beginPath();
        ctx.fillStyle = "#000";
        ctx.arc(left + 4, top + 6,
            s / 15, 0, 300, false);
        ctx.arc((left + s) - 4, top + 6,
            s / 15, 0, 300, false);
        ctx.closePath();
        ctx.fill();
    }

    function drawGhostEye(x, y, s, color, ctx) {
        const top = y + 2,
            left = x + 2;

        const base = top + s + 3;

        //draw eyes
        ctx.beginPath();
        ctx.fillStyle = "#FFF";
        ctx.arc(left + 4, top + 6, s / 6, 0, 300, false);
        ctx.arc((left + s) - 4, top + 6, s / 6, 0, 300, false);
        ctx.closePath();
        ctx.fill();

        ctx.beginPath();
        ctx.fillStyle = "#000";
        ctx.arc(left + 4, top + 6,
            s / 15, 0, 300, false);
        ctx.arc((left + s) - 4, top + 6,
            s / 15, 0, 300, false);
        ctx.closePath();
        ctx.fill();
    }


    function drawPacMan(x, y, s, angle1, angle2, color, ctx, direction) {
        // calculate angle by direction
        if (direction === "up") {
            angle1 = 1.75;
            angle2 = 1.25;
        } else if (direction === "down") {
            angle1 = 0.75;
            angle2 = 2.25;
        } else if (direction === "left") {
            angle1 = 1.25;
            angle2 = 0.75;
        } else if (direction === "right") {
            angle1 = 0.25;
            angle2 = 1.75;
        }
        ctx.fillStyle = color;

        ctx.beginPath();

        ctx.moveTo(x + s / 2,
            y + s / 2);

        ctx.arc(x + s / 2,
            y + s / 2,
            s / 2, Math.PI * angle1,
            Math.PI * angle2, false);
        ctx.fill();
    }

    function drawFruit(x, y, size, fruit, ctx) {
        if (fruit === "banana") {
            ctx.drawImage(banana, x, y, size, size);
        }
        if (fruit === "apple") {
            ctx.drawImage(apple, x, y, size, size);
        }
        if (fruit === 'quicksilver') {
            ctx.drawImage(quicksilver, x, y, size, size)
        }
    }

    reset();

    return {
        "draw": draw,
        "drawBlock": drawBlock,
        setMap,
        drawLargeDots,
        "height": height,
        "width": width,
        "blockSize": blockSize
    }
};

// UI logic
let PACMAN = (function () {
    // Template states to store variables that needs to be rendered
    let map = null;
    let boardId = undefined;
    // This is the default direction of the pacman
    let direction = "right";
    let gameState = Pacman.WATING;
    let mainLoopIntervalId = -1
    let renderIntervalId = -1

    function dialog(ctx, text) {
        ctx.fillStyle = "red";
        ctx.font = "40px CottonButter";
        let width = ctx.measureText(text).width,
            x = ((map.width * map.blockSize) - width) / 2;
        ctx.fillText(text, x, (map.height * 10) + 8);
    }

    function drawFooter(ctx, life, score, level) {
        const topLeft = (map.height * map.blockSize),
            textBase = topLeft + 20;

        ctx.fillStyle = "#000000";
        ctx.fillRect(0, topLeft, (map.width * map.blockSize), 30);
        ctx.fillStyle = "#FFFF00";

        for (let i = 0; i < life; i++) {
            ctx.fillStyle = "#FFFF00";
            ctx.beginPath();
            ctx.moveTo(220 + (25 * i) + map.blockSize / 2,
                (topLeft + 5) + map.blockSize / 2);

            ctx.arc(220 + (25 * i) + map.blockSize / 2,
                (topLeft + 5) + map.blockSize / 2,
                map.blockSize / 2, Math.PI * 0.25, Math.PI * 1.75, false);
            ctx.fill();
        }

        ctx.fillStyle = "#FFFF00";
        ctx.font = "20px CottonButter";

        ctx.fillText("Score: " + score, 30, textBase);
        ctx.fillText("Level: " + level, 450, textBase);
    }

    // This EventListener could be used to drive the other action in the game e.g. restart, pause, continue
    function detectKeyDown(e) {
        if (gameState !== Pacman.PLAYING) {
            return
        }
        if (e.keyCode === KEY.LEFT) {
            $.get('/' + boardId + '/change/left', {}, () => {
                direction = "left";
            }, 'json');
        } else if (e.keyCode === KEY.UP) {
            $.get('/' + boardId + '/change/up', {}, () => {
                direction = "up";
            }, 'json');
        } else if (e.keyCode === KEY.DOWN) {
            $.get('/' + boardId + '/change/down', {}, () => {
                direction = "down";
            }, 'json');
        } else if (e.keyCode === KEY.RIGHT) {
            $.get('/' + boardId + '/change/right', {}, () => {
                direction = "right";
            }, 'json');
        }
    }

    // This function should be the main logic of the game
    function mainLoop(ctx, boardId) {
        if (gameState === Pacman.PLAYING) {
            $.get('/' + boardId + '/board', {}, res => {
                if (res.success) {
                    map.setMap(res.data.board);
                    drawFooter(ctx, res.data.life, res.data.score, res.data.level);
                    if (res.data.life === 0) {
                        gameState = Pacman.GAMEOVER;
                    }
                }
            }, 'json');
        } else if (gameState === Pacman.GAMEOVER) {
            clearInterval(renderIntervalId)
            dialog(ctx, "GAME OVER, REFRESH TO RESTART");

        }
    }

    // root is unused for other element/backend data
    function init(element) {
        const initDialog = document.getElementById('initDialog');
        const warningField = document.getElementById('init-warning')
        initDialog.showModal();

        const startBtn = document.getElementById("startBtn");
        startBtn.addEventListener('click', async function start() {
            const life = document.getElementById('life').value;
            const ghost = document.getElementById('ghost').value;
            await $.get('/init/' + ghost + '/' + life, {}, (res) => {
                if (res.success) {
                    boardId = res.data;
                }
            }, 'json').error((err) => {
                warningField.hidden = false
                console.log(err)
                warningField.textContent = JSON.parse(err.responseText).message
                // console.error(err)
            });

            if (boardId !== undefined) {
                await $.get('/' + boardId + '/start', {}, res => {
                    gameState = Pacman.PLAYING;
                    document.addEventListener("keydown", detectKeyDown, true);
                    renderIntervalId = setInterval(() => map.draw(ctx, direction), 100);
                    mainLoopIntervalId = setInterval(() => {
                        mainLoop(ctx, boardId);
                    }, 150);

                }, 'json')

                initDialog.close();
            }
        })

        let blockSize = element.offsetWidth / Pacman.MAP[0].length;
        let canvas = document.createElement("canvas");

        canvas.setAttribute("width", (blockSize * Pacman.MAP[0].length) + "px");
        canvas.setAttribute("height", (blockSize * Pacman.MAP.length) + 30 + "px");

        element.appendChild(canvas);
        let ctx = canvas.getContext('2d');
        map = new Pacman.Map(blockSize);
    }

    return {
        init
    };
}());
