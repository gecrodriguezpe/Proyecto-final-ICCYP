import tkinter as tk
import random


# Allow the maze to be customized via command-line parameters
width = int(input("Enter the width of the maze: ") or 10)
height = int(input("Enter the height of the maze: ") or width)
seed = int(input("Enter the seed for randomness (or press Enter for a random seed): ") or random.randint(0, 0xFFFFFFF))


random.seed(seed)

# Set up constants to aid with describing the passage directions
N, S, E, W = 1, 2, 4, 8
DX = {E: 1, W: -1, N: 0, S: 0}
DY = {E: 0, W: 0, N: -1, S: 1}
OPPOSITE = {E: W, W: E, N: S, S: N}

# Data structures and methods to assist the algorithm
class Tree:
    def __init__(self):
        self.parent = None

    def root(self):
        return self.parent.root() if self.parent else self

    def connected(self, tree):
        return self.root() == tree.root()

    def connect(self, tree):
        tree.root().parent = self

# Initialize maze and sets
grid = [[0] * width for _ in range(height)]
sets = [[Tree() for _ in range(width)] for _ in range(height)]

# Build the list of edges
edges = []
for y in range(height):
    for x in range(width):
        if y > 0:
            edges.append((x, y, N))
        if x > 0:
            edges.append((x, y, W))

random.shuffle(edges)

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

def kruskal():
    for edge in edges:
        x, y, direction = edge
        nx, ny = x + DX[direction], y + DY[direction]

        set1, set2 = sets[y][x], sets[ny][nx]

        if not set1.connected(set2):
            draw_maze()

            set1.connect(set2)
            grid[y][x] |= direction
            grid[ny][nx] |= OPPOSITE[direction]

kruskal()
draw_maze()

root.mainloop()
