import tkinter as tk
import random
import time

# Allow the maze to be customized via command-line parameters
width = int(input("Enter the width of the maze: ") or 10)
height = int(input("Enter the height of the maze: ") or width)
seed = int(input("Enter the seed for randomness (or press Enter for a random seed): ") or random.randint(0, 0xFFFFFFF))
delay = float(input("Enter the delay between steps in seconds (or press Enter for 0.01): ") or 0.01)

random.seed(seed)

# Set up constants to aid with describing the passage directions
N, S, E, W = 1, 2, 4, 8
DX = {E: 1, W: -1, N: 0, S: 0}
DY = {E: 0, W: 0, N: -1, S: 1}
OPPOSITE = {E: W, W: E, N: S, S: N}

# Initialize maze and sets
grid = [[0] * width for _ in range(height)]
visited = [[False] * width for _ in range(height)]

# Build the list of edges for Prim's algorithm
edges = []

def add_neighbors(x, y):
    for direction in (N, S, E, W):
        nx, ny = x + DX[direction], y + DY[direction]
        if 0 <= nx < width and 0 <= ny < height and not visited[ny][nx]:
            priority = random.random()
            entry = (priority, (x, y, direction))
            edges.append(entry)

    edges.sort(key=lambda x: x[0])

# Tkinter visualization
root = tk.Tk()
canvas = tk.Canvas(root, width=20 * width, height=20 * height)
canvas.pack()

def draw_maze():
    canvas.delete("all")
    for y, row in enumerate(grid):
        for x, cell in enumerate(row):
            draw_cell(x, y, cell)

def draw_cell(x, y, cell):
    x_pixel, y_pixel = x * 20, y * 20

    if cell & N == 0:
        canvas.create_line(x_pixel, y_pixel, x_pixel + 20, y_pixel, fill="black")

    if cell & S == 0:
        canvas.create_line(x_pixel, y_pixel + 20, x_pixel + 20, y_pixel + 20, fill="black")

    if cell & E == 0:
        canvas.create_line(x_pixel + 20, y_pixel, x_pixel + 20, y_pixel + 20, fill="black")

    if cell & W == 0:
        canvas.create_line(x_pixel, y_pixel, x_pixel, y_pixel + 20, fill="black")

def prim():
    start_x, start_y = random.randint(0, width - 1), random.randint(0, height - 1)
    visited[start_y][start_x] = True
    add_neighbors(start_x, start_y)

    while edges:
        min_index = min(range(len(edges)), key=lambda i: edges[i][0])
        _, edge = edges.pop(min_index)
        x, y, direction = edge
        nx, ny = x + DX[direction], y + DY[direction]

        if not visited[ny][nx]:
            visited[ny][nx] = True
            grid[y][x] |= direction
            grid[ny][nx] |= OPPOSITE[direction]
            add_neighbors(nx, ny)
            draw_maze()
            time.sleep(delay)

prim()
draw_maze()

root.mainloop()
