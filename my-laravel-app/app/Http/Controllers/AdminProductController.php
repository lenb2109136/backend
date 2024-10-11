<?php

namespace App\Http\Controllers;

use Illuminate\Support\Facades\Storage;
use Illuminate\Support\Str;
use App\Models\Category;
use App\Models\Tag;
use Illuminate\Http\Request;
use App\Components\Recusive;
use App\Http\Requests\ProductAddRequest;
use App\Models\Product;
use App\Models\ProductImage;
use App\Models\ProductTag;
use App\Traits\StorageImageTrait;
use Illuminate\Support\Facades\Auth;
use Illuminate\Support\Facades\Log;
use Illuminate\Support\Facades\DB;


class AdminProductController extends Controller
{
    use StorageImageTrait;
    private $category;
    private $product;
    private $productImage;
    private $productTag;
    private $tag;
    public function __construct(Category $category, Product $product, ProductImage $productImage, Tag $tag, ProductTag $productTag)
    {

        $this->category = $category;
        $this->product = $product;
        $this->productImage = $productImage;
        $this->tag = $tag;
        $this->productTag = $productTag;
    }
    public function index()
    {
        $products =  $this->product->paginate(5);
        return view('admin.product.index', compact('products'));
    }
    public function create()
    {
        $htmlSelect = $htmlSelect = $this->getCategory($parent_id = '');
        return view('admin.product.add', compact('htmlSelect'));
    }
    public function getCategory($parent_id)
    {
        $data = $this->category::all();
        $recusive = new Recusive($data);
        $htmlSelect = $recusive->CategoryRecustive($parent_id);
        return $htmlSelect;
    }
    public function store(ProductAddRequest $request)
    {
        try {

            DB::beginTransaction();
            $dataProductCreate = [
                'name' => $request->name,
                'price' => $request->price,
                'content' => $request->contents,
                'user_id' => Auth::id(),
                'category_id' => $request->category_id,
            ];
            $dataUploadFeatureImage = $this->storageTraitUpload($request, 'feature_image_path', 'product');
            if (!empty($dataUploadFeatureImage)) {
                $dataProductCreate['feature_image_name'] = $dataUploadFeatureImage['filename'];
                $dataProductCreate['feature_image_path'] = $dataUploadFeatureImage['filepath'];
            }
            $product =  $this->product->create($dataProductCreate);

            //Insert data to product_images
            if ($request->hasFile('image_path')) {
                foreach ($request->image_path as $fileItem) {
                    $dataProductImageDetail = $this->storageTraitUploadMutiple($fileItem, 'product');
                    $product->images()->create([
                        'image_path' => $dataProductImageDetail['filepath'],
                        'image_name' => $dataProductImageDetail['filename'],
                    ]);
                }
            }

            //Insert data tags


            if (!empty($request->tags)) {
                foreach ($request->tags as $tagItem) {
                    $tagIntance = $this->tag->firstOrCreate(['name' => $tagItem]);
                    $tagIds[] = $tagIntance->id;
                }
            }
            $product->tags()->attach($tagIds);
            DB::commit();
            return redirect()->route('product.index');
        } catch (\Exception $exception) {
            DB::rollBack();
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
    }
    public function edit($id)
    {
        $product = $this->product->find($id);
        $htmlSelect = $htmlSelect = $this->getCategory($product->category_id);
        return view('admin.product.edit', compact('htmlSelect', 'product'));
    }
    public function update(Request $request, $id)
    {
        try {

            DB::beginTransaction();
            $dataProductUpdate = [
                'name' => $request->name,
                'price' => $request->price,
                'content' => $request->contents,
                'user_id' => Auth::id(),
                'category_id' => $request->category_id,
            ];
            $dataUploadFeatureImage = $this->storageTraitUpload($request, 'feature_image_path', 'product');
            if (!empty($dataUploadFeatureImage)) {
                $dataProductUpdate['feature_image_name'] = $dataUploadFeatureImage['filename'];
                $dataProductUpdate['feature_image_path'] = $dataUploadFeatureImage['filepath'];
            }
            $this->product->find($id)->update($dataProductUpdate);
            $product =  $this->product->find($id);

            //Insert data to product_images
            if ($request->hasFile('image_path')) {
                $this->productImage->where('product_id', $id)->delete();
                foreach ($request->image_path as $fileItem) {
                    $dataProductImageDetail = $this->storageTraitUploadMutiple($fileItem, 'product');
                    $product->images()->create([
                        'image_path' => $dataProductImageDetail['filepath'],
                        'image_name' => $dataProductImageDetail['filename'],
                    ]);
                }
            }

            //Insert data tags


            if (!empty($request->tags)) {
                foreach ($request->tags as $tagItem) {
                    $tagIntance = $this->tag->firstOrCreate(['name' => $tagItem]);
                    $tagIds[] = $tagIntance->id;
                }
            }
            $product->tags()->sync($tagIds);
            DB::commit();
            return redirect()->route('product.index');
        } catch (\Exception $exception) {
            DB::rollBack();
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
    }

    public function delete($id)
    {
        try {
            $this->product->find($id)->delete();
            return response()->json([
                'code' => 200,
                'message' => 'success',
            ], status: 200);
        } catch (\Exception $exception) {
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
            return response()->json([
                'code' => 500,
                'message' => 'fail',
            ], status: 500);
        }
    }
}
