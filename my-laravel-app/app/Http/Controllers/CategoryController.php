<?php

namespace App\Http\Controllers;

use Illuminate\Support\Str;
use App\Components\Recusive;
use App\Models\Category;
use App\Components\RecusiveComponents;
use Illuminate\Http\Request;

class CategoryController extends Controller
{
    private $category;

    public function __construct(Category $category)
    {
        $this->category = $category;
    }

    public function create()
    {
        $htmlSelect = $htmlSelect = $this->getCategory($parent_id = '');
        return view('admin.category.add', compact('htmlSelect'));
    }

    public function index()
    {
        $categories = $this->category->paginate(10);
        return view('admin.category.index', compact('categories'));
    }



    public function store(Request $request)
    {
        $this->category->create([
            'name' => $request->name,
            'parent_id' => $request->parent_id,
            'slug' => Str::slug($request->name),
        ]);
        return redirect()->route('admin.categories.index');
    }

    public function getCategory($parent_id)
    {
        $data = $this->category::all();
        $recusive = new Recusive($data);
        $htmlSelect = $recusive->CategoryRecustive($parent_id);
        return $htmlSelect;
    }

    public function edit($id)
    {
        $categories = $this->category->find($id);
        $htmlSelect = $this->getCategory($categories->parent_id);
        return view('admin.category.edit', compact('categories', 'htmlSelect'));
    }
    public function update($id, Request $request)
    {
        $this->category->find($id)->update([
            'name' => $request->name,
            'parent_id' => $request->parent_id,
            'slug' => Str::slug($request->name),
        ]);
        return redirect()->route('admin.categories.index');
    }

    public function delete($id)
    {
        $this->category->find($id)->delete();
        return redirect()->route('admin.categories.index');
    }
}
