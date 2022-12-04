import re
from argparse import ArgumentParser

argument_parser = ArgumentParser()

argument_parser.add_argument("filename", help="Input path", type=str)
argument_parser.add_argument("--output", "-o", help="Output path", type=str)

arguments = argument_parser.parse_args()


def is_empty(input_str):
    return input_str.strip() == ""


class Sector:
    def __init__(self, id, title, parent):
        self.id = id
        self.title = title
        self.parent = parent


filename = arguments.filename
if filename is None or is_empty(filename):
    raise ValueError("File name is not defined")
category_item_pattern = '&nbsp;'
category_second_level = category_item_pattern * 4
category_third_level = category_second_level * 2
category_fourth_level = category_second_level * 3
html_file = open(f"./{filename}")
file_content = html_file.read()
html_file.close()
tokens = re.findall(r'<option value="(\d+)">(.*)</option>', file_content)
sectors = []


def find_parent(index, id, title, level):
    slice = tokens[:index]
    slice.reverse()
    title = title.replace('&nbsp;', '')
    for (parent_id, parent_title) in slice:
        if level == 2 and category_second_level not in parent_title:
            print(f"Found parent {parent_id}. {parent_title} for {id}. {title}")
            return Sector(id, title, parent_id)
        elif level == 3 and category_third_level not in parent_title and category_second_level in parent_title:
            print(f"Found parent {parent_id}. {parent_title} for {id}. {title}")
            return Sector(id, title, parent_id)
        elif level == 4 and category_fourth_level not in parent_title and category_third_level in parent_title:
            print(f"Found parent {parent_id}. {parent_title} for {id}. {title}")
            return Sector(id, title, parent_id)


for index, (id, title) in enumerate(tokens):
    title = title.replace('&amp;', '&')
    if category_fourth_level in title:
        sector = find_parent(index, id, title, 4)
    elif category_third_level in title:
        sector = find_parent(index, id, title, 3)
    elif category_second_level in title:
        sector = find_parent(index, id, title, 2)
    elif category_second_level not in title:
        sector = Sector(id, title, None)
    sectors.append(sector)

sqls = []
for sector in sectors:
    print(f"Found {len(sectors)} total")
    parent = 'NULL' if sector.parent is None else sector.parent
    sqls.append(
        f"""INSERT INTO sector(id, title, parent_id) VALUES ({sector.id}, '{sector.title}', {parent});\n""")

sql_file = open(f"{arguments.output}", "w")
sql_file.writelines(sqls)
sql_file.close()
