<?php

namespace App\Http\Controllers;

use App\Http\Requests\AddSettingRequest;
use App\Models\Setting;
use Illuminate\Http\Request;
use Illuminate\Support\Facades\Log;

class AdminSettingController extends Controller
{
    private $setting;
    public function __construct(Setting $setting)
    {
        $this->setting = $setting;
    }
    public function index()
    {
        $settings =  $this->setting->paginate(5);
        return view('admin.setting.index', compact('settings'));
    }
    public function create()
    {
        return view('admin.setting.add');
    }
    public function store(AddSettingRequest $request)
    {
        try {
            $dataSettingInsert = [
                'config_key' => $request->config_key,
                'config_value' => $request->config_value,
            ];
            $this->setting->create($dataSettingInsert);
            return redirect()->route('settings.index');
        } catch (\Exception $exception) {
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
    }
    public function edit($id)
    {
        $settings = $this->setting->find($id);
        return view('admin.setting.edit', compact('settings'));
    }
    public function update(AddSettingRequest $request, $id)
    {
        try {
            $dataSettingInsert = [
                'config_key' => $request->config_key,
                'config_value' => $request->config_value,
            ];
            $this->setting->find($id)->update($dataSettingInsert);
            return redirect()->route('settings.index');
        } catch (\Exception $exception) {
            Log::error('message ' . $exception->getMessage() . ' Line ' . $exception->getLine());
        }
    }
    public function delete($id)
    {
        try {
            $this->setting->find($id)->delete();
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
