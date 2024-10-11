<?php

namespace App\Http\Controllers;

use Illuminate\Support\Str;
use App\Components\Recusive;
use App\Models\Category;
use App\Components\MenuRecusive;
use App\Models\Menu;
use Illuminate\Http\Request;

class MenuController extends Controller
{
    private $menuRecusive;
    private $menu;
    public function __construct(MenuRecusive $menuRecusive, Menu $menu)
    {
        $this->menuRecusive = $menuRecusive;
        $this->menu = $menu;
    }

    public function index()
    {
        $menus = $this->menu->paginate(10);
        return view('admin.menus.index', compact('menus'));
    }
    public function create()
    {
        $optionSelect = $this->menuRecusive->menuRecustiveAdd();
        return view('admin.menus.add', compact('optionSelect'));
    }

    public function store(Request $request)
    {
        $this->menu->create([
            'name' => $request->name,
            'parent_id' => $request->parent_id,
            'slug' => Str::slug($request->name),
        ]);
        return redirect()->route('menus.index');
    }

    public function getCategory($parent_id)
    {
        $data = $this->menu::all();
        $recusive = new Recusive($data);
        $htmlSelect = $recusive->CategoryRecustive($parent_id);
        return $htmlSelect;
    }

    public function edit($id)
    {
        $menus = $this->menu->find($id);
        $optionSelect = $this->getCategory($menus->parent_id);
        return view('admin.menus.edit', compact('menus', 'optionSelect'));
    }

    public function update($id, Request $request)
    {
        $this->menu->find($id)->update([
            'name' => $request->name,
            'parent_id' => $request->parent_id,
            'slug' => Str::slug($request->name),
        ]);
        return redirect()->route('menus.index');
    }

    public function delete($id)
    {
        $this->menu->find($id)->delete();
        return redirect()->route('menus.index');
    }
}
