@extends('layouts.admin')

@section('title')
    <title>Settings</title>
@endsection
@section('css')
    <link rel="stylesheet" href="{{ asset('admins/setting/add/add.css') }}">
@endsection
@section('content')
    <div class="content-wrapper">
        <!-- Content Header (Page header) -->
        @include('partials.content-header', ['name' => 'Setting', 'key' => 'Add'])

        <div class="container-fluid">
            <div class="row">
                <div class="col-md-6">
                    <form action="{{ route('settings.update', ['id' => $settings->id]) }}" method="POST">
                        @csrf
                        <div class="form-group">
                            <label class="@error('config_key') is-invalid @enderror">Config Key</label>
                            <input type="text" name="config_key"
                                class="form-control  @error('config_key') is-invalid @enderror"
                                placeholder="Nhập tên config key" value="{{ $settings->config_key }}">
                            @error('config_key')
                                <div class="alert alert-fix alert-danger">{{ $message }}</div>
                            @enderror
                        </div>

                        @if (request()->type == 'textarea')
                            <div class="form-group">
                                <label class="@error('config_value') is-invalid @enderror">Config value</label>
                                <textarea name="config_value" class="form-control @error('config_value') is-invalid @enderror"
                                    placeholder="Nhập tên config value">{{ $settings->config_value }}</textarea>
                                @error('config_value')
                                    <div class="alert alert-fix alert-danger">{{ $message }}</div>
                                @enderror
                            </div>
                        @else
                            <div class="form-group">
                                <label class="@error('config_value') is-invalid @enderror">Config value</label>
                                <input type="text" name="config_value"
                                    class="form-control @error('config_value') is-invalid @enderror"
                                    placeholder="Nhập tên config value" value="{{ $settings->config_value }}">
                                @error('config_value')
                                    <div class="alert alert-fix alert-danger">{{ $message }}</div>
                                @enderror
                            </div>
                        @endif



                        <button type="submit" class="btn btn-primary">Submit</button>
                    </form>
                </div>

            </div>

            <!-- /.row -->
        </div><!-- /.container-fluid -->
    </div>

    </div>
@endsection
